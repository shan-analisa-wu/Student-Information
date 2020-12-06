/**
 * Represents the Hash Table.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12.03.2019
 */
public class HashTable {
    /**
     * number of slots in a single bucket.
     */
    private final int slotSize = 32;
    /**
     * Given "heap size" from the command line argument #2.
     */
    private int numOfBuckets; // command line input
    /**
     * The actual heap size = slot size * number of buckets.
     */
    private int hashSize;
    /**
     * The Hash Table for Student Object.
     */
    private Student[] students;
    /**
     * Keep track with the current size of every bucket.
     */
    private int[] bucketSize;


    /**
     * The constructor of the HashTable class.
     * 
     * @param tableSize
     *            Given "heap size" from the command line argument #2.
     */
    public HashTable(int tableSize) {
        this.numOfBuckets = tableSize / slotSize;
        hashSize = tableSize;
        students = new Student[hashSize];
        bucketSize = new int[numOfBuckets];
        System.out.println("Created Hash Set with " + tableSize + " slots.");
    }


    /**
     * Get the number of bucket
     * 
     * @return the number of bucket
     */
    public int getNumOfBuckets() {
        return numOfBuckets;
    }


    /**
     * Get the number of the total slots
     * 
     * @return the number of total slots
     */
    public int getHashSize() {
        return hashSize;
    }


    /**
     * Get the number of slots in each block
     * 
     * @return the number of slots in each slot
     */
    public int[] getBucketSize() {
        return bucketSize;
    }


// ---------------insertion and its helper functions---------------//
    /**
     * This method checks whether a slot is empty
     * 
     * @param currIdx
     *            The current index in the array
     * @return return whether a slot is empty
     */
    private boolean slotIsEmpty(int currIdx) {
        return students[currIdx] == null; // then we can add.
    }


    /**
     * Determine if the currIdx reaches the end of the slot.
     * end position: 31, 63, ...
     * 31 + 1 % 32 == 0, 63 + 1 % 32 == 0, ...
     * 
     * @param currIdx
     *            The current index in the array
     * @return return whether the end of a block is reached
     */
    private boolean slotEndPosReached(int currIdx) {
        return (currIdx % 32 == 0);
    }


    /**
     * Helper function For the bucketSize[] array:
     * Get the bucket number for the input currIdx.
     * 2 / 32 => 0, 32/32 => 1, ...
     * 
     * @param currIdx
     *            The current index in the array
     * @return return the number of bucket that are currently in
     */
    private int calCurrBucket(int currIdx) {
        return currIdx / slotSize;
    }


    /**
     * This method insert a student to the hash table
     * 
     * @param student
     *            The student to be inserted
     * @return return whether the student is successfully inserted
     */
    public int insert(Student student) {
        int currIdx = (int)sFold(student.getPid(), hashSize);
        int currBucket = calCurrBucket(currIdx);
        if (bucketSize[currBucket] == 32) {
            return -1; // failed: the bucket is full.
        }

        while (true) {
            if (slotIsEmpty(currIdx)) {
                students[currIdx] = student;
                bucketSize[currBucket]++;
                break;
            }
            currIdx++;
            if (slotEndPosReached(currIdx)) {
                currIdx = currBucket * 32; // Position: start from the beginning
                                           // of the slot: 0 -> 0, 1 -> 32, ...
            }
        }
        return 1; // success
    }

// ---------------insertion and its help functions---------------//


    /**
     * This method removes a student from the hashtable
     * 
     * @param pid
     *            the pid of the student to be removed
     * @return return whether the student has been successfully removed from the
     *         hashtable
     */
    public int remove(String pid) {
        int foundIdx = findHelp(pid);
        if (foundIdx == -1) {
            return -1; // failed: cannot find the pid.
        }
        students[foundIdx] = null;
        bucketSize[calCurrBucket(foundIdx)]--;
        return 1; // success
    }


    /**
     * This method remove the name handle of a student from the hashtable
     * 
     * @param pid
     *            The pid of the student
     * @return return whether the name handle of the student has been
     *         successfully removed
     */
    public int clearName(String pid) {
        int foundIdx = findHelp(pid);
        if (foundIdx == -1) {
            return -1; // failed: cannot find the pid.
        }
        students[foundIdx].setNameHandle(new Handle(-1, 0));
        return 1; // success
    }


    /**
     * This method find a particular student in the hashtable
     * 
     * @param pid
     *            The pid of the student to be found
     * @return return the student if successfully found the student,
     *         otherwise, return null
     */
    public Student find(String pid) {
        int foundIdx = findHelp(pid);
        if (foundIdx == -1) {
            return null;
        }
        return students[foundIdx];
    }


    /**
     * A helper function to help the target index.
     * 
     * @param pid
     *            the pid of the student.
     * @return the target index or -1 if cannot find the pid.
     */
    private int findHelp(String pid) {
        int currIdx = (int)sFold(pid, hashSize);
        int currBucket = calCurrBucket(currIdx);
        for (int i = currBucket * 32; i < ((currBucket + 1) * 32); i++) {
            if (students[i] != null) {
                if (students[i].getPid().equals(pid)) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * This method update the name handle of a student
     * 
     * @param pid
     *            The pid of the student to be updated
     * @param handle
     *            The new name handle of the student
     */
    public void updateNameHandle(String pid, Handle handle) {
        int foundIdx = findHelp(pid);
        if (foundIdx != -1) {
            students[foundIdx].setNameHandle(handle);
        }
    }


    /**
     * This method update the essay handle of a student
     * 
     * @param pid
     *            The pid of the student to be updated
     * @param handle
     *            The new essay handle of the student
     */
    public void updateEssayHandle(String pid, Handle handle) {
        int foundIdx = findHelp(pid);
        if (foundIdx != -1) {
            students[foundIdx].setEssayHandle(handle);
        }
    }


    /**
     * This method returns the student array
     * 
     * @return The student array
     */
    public Student[] getStudentArray() {
        return students;
    }


    /**
     * The hash function
     * 
     * @param s
     *            The pid of a student
     * @param m
     *            The hash table size
     * @return return a possible position to insert the student
     */
    private long sFold(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        sum = (sum * sum) >> 8;
        return (Math.abs(sum) % m);
    }

}
