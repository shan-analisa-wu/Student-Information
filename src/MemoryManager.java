import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/**
 * Create a new MemoryManager object
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12/9/2019
 */
public class MemoryManager {
    private RandomAccessFile memFile;
    private HashTable hash;
    private LinkedList<Handle> freeList;
    private Student lastStudent;


    /**
     * Create a new memory manager object
     * 
     * @param mem
     *            The name of the memory file
     * @param tableSize
     *            The hash table size
     */
    public MemoryManager(String mem, int tableSize) {
        hash = new HashTable(tableSize);
        try {
            memFile = new RandomAccessFile(mem, "rw");
            memFile.setLength(0);
            memFile.seek(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        freeList = new LinkedList<Handle>();
    }


    /**
     * This method inserts student read from the given csv file
     * 
     * @param pid
     *            The pid of the student
     * @param name
     *            The name of the student
     * @return Return the name handle of the student if successfully inserted,
     *         otherwise,
     *         return null
     * @throws IOException
     *             The input output exception
     */
    public Handle csvInsert(String pid, Name name) throws IOException {
        Student student = hash.find(pid);
        if (student != null) {
            System.out.println("Warning: Student " + pid + " " + name
                + " is not loaded since a student with the same pid exists");
            return null;
        }
        else {
            String fullName = name.toString();
            byte[] nameByte = fullName.getBytes();
            if (freeList.peekFirst() != null) {
                for (Handle current : freeList) {
                    if (current.getLength() >= nameByte.length) {
                        long start = current.getStart();
                        long length = nameByte.length;
                        Handle nameHandle = new Handle(start, length);
                        Handle essayHandle = new Handle(-1, -1);
                        Student newStd = new Student(pid, nameHandle,
                            essayHandle);
                        int inserted = hash.insert(newStd);
                        if (inserted == -1) {
                            System.out.println("Warning: There is no "
                                + "free space in the bucket to load"
                                + " student " + pid + " " + name);
                            return null;
                        }
                        else {
                            memFile.seek(current.getStart());
                            memFile.write(nameByte);
                            long newLen = current.getLength() - length;
                            if (newLen != 0) {
                                long newStart = start + length;
                                current.setStart(newStart);
                                current.setLength(newLen);
                            }
                            else {
                                freeList.remove(current);
                            }
                            return nameHandle;
                        }
                    }
                }
            }
            long start = memFile.length();
            Handle nameHandle = new Handle(start, nameByte.length);
            Handle essayHandle = new Handle(-1, -1);
            Student newStd = new Student(pid, nameHandle, essayHandle);
            int inserted = hash.insert(newStd);
            if (inserted == -1) {
                System.out.println(
                    "Warning: There is no free space in the bucket to load"
                        + " student " + pid + " " + name);
                return null;
            }
            else {
                memFile.seek(start);
                memFile.write(nameByte);
                return nameHandle;
            }
        }
    }


    /**
     * This method insert a student to the database
     * 
     * @param pid
     *            The pid of the student to be inserted
     * @param name
     *            The name of the student to be inserted
     * @return Return the name handle of the student if successfully inserted,
     *         otherwise, return null
     * @throws IOException
     *             The input output exception
     */
    public Handle insert(String pid, Name name) throws IOException {
        Student student = hash.find(pid);
        if (student != null) {
            System.out.println(name + " insertion failed since the pid " + pid
                + " belongs to another student");
            return null;
        }
        else {
            String fullName = name.toString();
            byte[] nameByte = fullName.getBytes();
            if (freeList.peekFirst() != null) {
                for (Handle current : freeList) {
                    if (current.getLength() >= nameByte.length) {
                        long start = current.getStart();
                        long length = nameByte.length;
                        Handle nameHandle = new Handle(start, length);
                        Handle essayHandle = new Handle(-1, -1);
                        Student newStd = new Student(pid, nameHandle,
                            essayHandle);
                        int inserted = hash.insert(newStd);
                        if (inserted == -1) {
                            System.out.println(name
                                + " insertion failed. Attempt to insert in "
                                + "a full bucket.");
                            return null;
                        }
                        else {
                            memFile.seek(current.getStart());
                            memFile.write(nameByte);
                            long newLen = current.getLength() - length;
                            if (newLen != 0) {
                                long newStart = start + length;
                                current.setStart(newStart);
                                current.setLength(newLen);
                            }
                            else {
                                freeList.remove(current);
                            }
                            lastStudent = newStd;
                            System.out.println(name + " inserted");
                            return nameHandle;
                        }
                    }
                }
            }
            long start = memFile.length();
            Handle nameHandle = new Handle(start, nameByte.length);
            Handle essayHandle = new Handle(-1, -1);
            Student newStd = new Student(pid, nameHandle, essayHandle);
            int inserted = hash.insert(newStd);
            if (inserted == -1) {
                System.out.println(name
                    + " insertion failed. Attempt to insert in "
                    + "a full bucket.");
                return null;
            }
            else {
                memFile.seek(start);
                memFile.write(nameByte);
                lastStudent = newStd;
                System.out.println(name + " inserted");
                return nameHandle;
            }
        }
    }


    /**
     * This method searches for a particular student in the database
     * 
     * @param pid
     *            The pid of the student to be searched
     * @return return true if the student is in the database, otherwise,
     *         return false
     * @throws IOException
     *             The input output exception
     */
    public boolean search(String pid) throws IOException {
        Student student = hash.find(pid);
        if (student != null) {
            Handle name = student.getNameHandle();
            memFile.seek(name.getStart());
            byte[] nameByte = new byte[(int)name.getLength()];
            memFile.read(nameByte);
            String nameStr = new String(nameByte);
            System.out.println(pid + " " + nameStr + ":");
            Handle essay = student.getEssayHandle();
            if (essay.getStart() != -1) {
                byte[] essayByte = new byte[(int)essay.getLength()];
                memFile.seek(essay.getStart());
                memFile.read(essayByte);
                String esyStr = new String(essayByte);
                System.out.println(esyStr);
            }
            return true;
        }
        System.out.println("Search Failed. Couldn't find any student with ID "
            + pid);
        return false;
    }


    /**
     * The method remove a particular student from the database
     * 
     * @param pid
     *            The pid of the student to be removed
     * @return return true if the student is successfully removed,
     *         otherwise, return false
     * @throws IOException
     *             The input output exception
     */
    public boolean remove(String pid) throws IOException {
        Student student = hash.find(pid);
        if (student == null) {
            System.out.println(pid + " is not found in the database");
            return false;
        }
        else {
            Handle nameHandle = student.getNameHandle();
            Handle essayHandle = student.getEssayHandle();
            Handle name = student.getNameHandle();
            memFile.seek(name.getStart());
            byte[] nameByte = new byte[(int)name.getLength()];
            memFile.read(nameByte);
            String nameStr = new String(nameByte);
            System.out.println(pid + " with full name " + nameStr
                + " is removed from the database.");
            hash.remove(pid);
            delete(nameHandle);
            if (essayHandle.getStart() != -1) {
                delete(essayHandle);
            }
            return true;
        }
    }


    /**
     * This method removes the essay of the student from the database
     * 
     * @param pid
     *            The pid of the student
     * @return return true if the essay of the student is successfully removed,
     *         otherwise, return false
     * @throws IOException
     *             The input output exception
     */
    public boolean clear(String pid) throws IOException {
        Student student = hash.find(pid);
        if (student == null) {
            System.out.println(pid + " is not found in the database");
            return false;
        }
        else {
            Handle essayHandle = student.getEssayHandle();
            Handle newEssay = new Handle(-1, 0);
            hash.updateEssayHandle(student.getPid(), newEssay);
            Handle name = student.getNameHandle();
            memFile.seek(name.getStart());
            byte[] nameByte = new byte[(int)name.getLength()];
            memFile.read(nameByte);
            String nameStr = new String(nameByte);
            System.out.println("record with pid " + pid + " with full name "
                + nameStr + " is cleared.");
            if (essayHandle.getStart() != -1) {
                delete(essayHandle);
            }
            return true;
        }

    }


    /**
     * A helper method that put a free space in the memory file
     * into the free list as handle
     * 
     * @param handle
     *            The handle that need to be removed from the
     *            memory file
     * @throws IOException
     *             The input output exception
     */
    private void delete(Handle handle) throws IOException {
        long start = handle.getStart();
        if (freeList.peekFirst() == null) {
            Handle free = new Handle(start, handle.getLength());
            freeList.add(free);
        }
        else {
            int flag = 0;
            int count = 0;
            for (Handle current : freeList) {
                if (current.getStart() + current.getLength() == start) {
                    current.setLength(current.getLength() + handle.getLength());
                    flag = 1;
                }
                else if (start + handle.getLength() == current.getStart()
                    && flag == 0) {
                    current.setStart(handle.getStart());
                    current.setLength(current.getLength() + handle.getLength());
                    flag = 1;
                }
                if (count != 0) {
                    Handle prev = freeList.get(count - 1);
                    if (prev.getStart() + prev.getLength() == current
                        .getStart()) {
                        prev.setLength(prev.getLength() + current.getLength());
                        freeList.remove(current);
                    }
                }
                count++;
            }
            int curPos = 0;
            if (flag == 0) {
                for (Handle curHandle : freeList) {
                    if (curHandle.getStart() < start) {
                        curPos++;
                    }
                }
                freeList.add(curPos, handle);
            }
        }
        Handle last = freeList.peekLast();
        if (last != null) {
            if ((last.getStart() + last.getLength()) == memFile.length()) {
                freeList.remove(last);
                memFile.setLength(last.getStart());
            }
        }
    }


    /**
     * This method prints out the student in the database and
     * the free list
     * 
     * @throws IOException
     *             The input output exception
     */
    public void print() throws IOException {
        System.out.println("Students in the database:");
        Student[] stds = hash.getStudentArray();
        int hashSize = hash.getHashSize();
        for (int i = 0; i < hashSize; i++) {
            int slot = i;
            Student student = stds[i];
            if (student != null) {
                Handle name = student.getNameHandle();
                memFile.seek(name.getStart());
                byte[] nameByte = new byte[(int)name.getLength()];
                memFile.read(nameByte);
                String nameStr = new String(nameByte);
                System.out.println(nameStr + " at slot " + slot);
            }
        }
        System.out.println("Free Block List:");
        for (int i = 0; i < freeList.size(); i++) {
            System.out.println("Free Block " + (i + 1) + " starts from Byte "
                + freeList.get(i).getStart() + " with length " + freeList.get(i)
                    .getLength());
        }
    }


    /**
     * This method write the essay of the student into the database
     * 
     * @param essayStr
     *            The essay to be written
     * @return return the essay handle
     * @throws IOException
     *             The input output exception
     */
    public Handle essayOff(StringBuilder essayStr) throws IOException {
        String essay = essayStr.toString();
        byte[] essayByte = essay.getBytes();
        Handle oldEssay = lastStudent.getEssayHandle();
        if (oldEssay.getStart() != -1) {
            byte[] oldEssayByte = new byte[(int)oldEssay.getLength()];
            memFile.seek(oldEssay.getStart());
            memFile.read(oldEssayByte);
            String oldEssayStr = new String(oldEssayByte);
            oldEssayStr = oldEssayStr.toLowerCase();
            essay = essay.toLowerCase();
            if (!oldEssayStr.equals(essay)) {
                delete(oldEssay);
            }
            else {
                return null;
            }
        }
        if (freeList.peekFirst() != null) {
            for (Handle current : freeList) {
                if (current.getLength() >= essayByte.length) {
                    long start = current.getStart();
                    long length = essayByte.length;
                    Handle newEssay = new Handle(start, length);
                    hash.updateEssayHandle(lastStudent.getPid(), newEssay);
                    memFile.seek(start);
                    memFile.write(essayByte);
                    long newLen = current.getLength() - length;
                    if (newLen != 0) {
                        long newStart = start + length;
                        current.setStart(newStart);
                        current.setLength(newLen);
                    }
                    else {
                        freeList.remove(current);
                    }
                    Handle name = lastStudent.getNameHandle();
                    memFile.seek(name.getStart());
                    byte[] nameByte = new byte[(int)name.getLength()];
                    memFile.read(nameByte);
                    String nameStr = new String(nameByte);
                    System.out.println("essay saved for " + nameStr);
                    return newEssay;
                }
            }
        }
        long start = memFile.length();
        Handle newEssay = new Handle(start, essayByte.length);
        memFile.seek(start);
        memFile.write(essayByte);
        hash.updateEssayHandle(lastStudent.getPid(), newEssay);
        Handle name = lastStudent.getNameHandle();
        memFile.seek(name.getStart());
        byte[] nameByte = new byte[(int)name.getLength()];
        memFile.read(nameByte);
        String nameStr = new String(nameByte);
        System.out.println("essay saved for " + nameStr);
        return newEssay;
    }


    /**
     * This method update the name of a student
     * 
     * @param pid
     *            The pid of the student to be updated
     * @param name
     *            The name of the student to be updated to
     * @return return true if the student is successfully updated, otherwise,
     *         return false
     * @throws IOException
     *             The input output exception
     */
    public boolean update(String pid, Name name) throws IOException {
        Student student = hash.find(pid);
        if (student == null) {
            Handle inserted = insert(pid, name);
            return inserted != null;
        }
        else {
            Handle oriName = student.getNameHandle();
            memFile.seek(oriName.getStart());
            byte[] nameByte = new byte[(int)oriName.getLength()];
            memFile.read(nameByte);
            String nameStr = new String(nameByte);
            String newName = name.toString();
            byte[] newNameByte = newName.getBytes();
            nameStr = nameStr.toLowerCase();
            newName = newName.toLowerCase();
            if (!nameStr.equals(newName)) {
                delete(oriName);
                int written = 0;
                for (Handle current : freeList) {
                    if (current.getLength() >= newNameByte.length) {
                        long start = current.getStart();
                        long length = newNameByte.length;
                        Handle nameHandle = new Handle(start, length);
                        memFile.seek(start);
                        memFile.write(newNameByte);
                        hash.updateNameHandle(pid, nameHandle);
                        long newLen = current.getLength() - length;
                        if (newLen != 0) {
                            long newStart = start + length;
                            current.setStart(newStart);
                            current.setLength(newLen);
                        }
                        else {
                            freeList.remove(current);
                        }
                        written = 1;
                        System.out.println("Student " + pid + " updated to "
                            + name);
                    }
                }
                if (written != 1) {
                    long start = memFile.length();
                    Handle updateName = new Handle(start, newNameByte.length);
                    memFile.seek(start);
                    memFile.write(newNameByte);
                    hash.updateNameHandle(lastStudent.getPid(), updateName);
                    System.out.println("Student " + pid + " updated to "
                        + name);
                }
            }
            else {
                System.out.println("Student " + pid + " updated to " + name);
            }
            lastStudent = student;
            return true;
        }
    }

}
