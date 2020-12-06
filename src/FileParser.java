import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * a class for reading the given command file.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 09.20.2019
 */
public class FileParser {
    private CommandsExecution ce;


    /**
     * The constructor for reading the input file.
     * 
     * @param cmdFileName
     *            the name of cmd file
     * @param memFile
     *            the memory file
     * @param tableSize
     *            the hash table size
     */
    public FileParser(String cmdFileName, String memFile, int tableSize) {
        ce = new CommandsExecution(memFile, tableSize);
        readCmdFile(cmdFileName);
    }


    /**
     * A helper function for reading the .txt file
     * 
     * @param cmdFileName
     *            the name of the cmd file.
     */
    private void readCmdFile(String cmdFileName) {
        String line;
        try {
            RandomAccessFile raf = new RandomAccessFile(cmdFileName, "r");
            while ((line = raf.readLine()) != null) {
                ce.checkCommands(line);
            }
            raf.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("Cannot find the file: " + cmdFileName);
        }
        catch (IOException ex) {
            System.err.println("Unable to access the file: " + cmdFileName);
        }
    }
}
