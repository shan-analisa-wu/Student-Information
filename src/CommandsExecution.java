import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Commands Execution
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 09.20.2019
 */
public class CommandsExecution {
    private String lastestCommand;
    private MemoryManager mem;
    private int isEssay;
    private StringBuilder essayStr;


    /**
     * Constructor, create a new CommandsExecution object.
     * 
     * @param memFile
     *            the memory file
     * @param tableSize
     *            the hash table size
     */

    public CommandsExecution(String memFile, int tableSize) {
        mem = new MemoryManager(memFile, tableSize);
        isEssay = 0;
        essayStr = new StringBuilder();
    }


    /**
     * check commands in the .txt file
     * line by line and determine which
     * command to execute according to
     * the input file
     * 
     * @param line
     *            A line that specifies a command
     *            to be executed.
     * @throws IOException
     */
    public void checkCommands(String line) throws IOException {
        if (line != null) {
            String temp = line;
            line = line.trim();
            String[] words = line.split("\\s+");
            if (isEssay == 1 && !temp.contains("essay off")) {
                essayStr.append(temp);
                essayStr.append("\n");
            }
            switch (words[0]) {
                case "loadstudentdata":
                    csvStudentReader(words[1]);
                    lastestCommand = "loadstudentdata";
                    break;
                case "insert":
                    Name name = new Name(words[2], words[3]);
                    Handle handle = mem.insert(words[1], name);
                    if (handle != null) {
                        lastestCommand = words[0];
                    }
                    else {
                        lastestCommand = "failed";
                    }
                    break;
                case "clear":
                    mem.clear(words[1]);
                    lastestCommand = "clear";
                    break;
                case "update":
                    Name name2 = new Name(words[2], words[3]);
                    boolean success = mem.update(words[1], name2);
                    if (success) {
                        lastestCommand = words[0];
                    }
                    else {
                        lastestCommand = "failed";
                    }
                    break;
                case "remove":
                    mem.remove(words[1]);
                    lastestCommand = "remove";
                    break;
                case "search":
                    mem.search(words[1]);
                    lastestCommand = "search";
                    break;
                case "print":
                    mem.print();
                    lastestCommand = "print";
                    break;
                case "essay":
                    if (lastestCommand.equals("insert") || lastestCommand
                        .equals("update")) {
                        if (words[1].equals("on")) {
                            isEssay = 1;
                        }
                        if (words[1].equals("off")) {
                            isEssay = 0;
                            mem.essayOff(essayStr);
                            essayStr = new StringBuilder();
                            lastestCommand = "essay off";
                        }
                    }
                    else {
                        if (words[1].equals("on")) {
                            System.out.println(
                                "essay commands can only follow successful "
                                    + "insert or update commands");
                        }
                    }

                    break;
                default:
                    break;
            }
        }
        else {
            if (isEssay == 1) {
                essayStr.append("\n");
            }
        }
    }


    /**
     * Read the student .csv file and store the data into the BST of the local
     * student manager
     * 
     * @param csvName
     *            The name of the student .csv file.
     */
    private void csvStudentReader(String csvName) {
        System.out.println(csvName + " successfully loaded.");
        String line;
        try {
            RandomAccessFile raf = new RandomAccessFile(csvName, "rw");
            while ((line = raf.readLine()) != null) {
                String[] str = line.split("\\,");
                Name newName;
                if (str.length > 0) {
                    if (str[2].equals("")) {
                        newName = new Name(str[1], str[3]);
                    }
                    else {
                        newName = new Name(str[1], str[3], str[2]);
                    }
                    mem.csvInsert(str[0], newName);
                }
            }
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * get lastest command
     * 
     * @return lastest command
     */
    public String getLastestCommand() {
        return lastestCommand;
    }
}
