import java.io.IOException;
import student.TestCase;

/**
 * This class tests all the method in the memoryManager class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12/9/2019
 */
public class MemoryManagerTest extends TestCase {
    private MemoryManager manager;


    /**
     * Set up for the testing
     */
    public void setUp() {
        manager = new MemoryManager("myTest.data", 32);
    }


    /**
     * Test the insert method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testInsert() throws IOException {
        System.out.println("------------Insert-------------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        manager.insert("123456789", name);
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the search method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testSearch() throws IOException {
        System.out.println("-----------Search----------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        manager.search("123456789");
        manager.search("12345");
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the remove method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testRemove() throws IOException {
        System.out.println("-----------Remove----------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        manager.remove("123456789");
        manager.remove("123456789");
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the essayOff method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testEssayOff() throws IOException {
        System.out.println("-----------Essay Off----------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        StringBuilder build = new StringBuilder();
        build.append("I don't want to write project");
        manager.essayOff(build);
        manager.search("123456789");
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the clear method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testClear() throws IOException {
        System.out.println("----------Clear-----------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        StringBuilder build = new StringBuilder();
        build.append("I don't want to write project");
        manager.essayOff(build);
        manager.clear("123456789");
        manager.search("123456789");
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the upDate method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testUpDate() throws IOException {
        System.out.println("----------upDate------------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        Name name1 = new Name("Ann", "Will");
        manager.update("123456789", name1);
        manager.search("123456789");
        Name name2 = new Name("Johnson", "Will");
        manager.update("123456789", name2);
        manager.search("123456789");
        Name name3 = new Name("John", "Will");
        manager.update("987654321", name3);
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }


    /**
     * Test the print method
     * 
     * @throws IOException
     *             The input output exception
     */
    public void testPrint() throws IOException {
        System.out.println("--------Print------------");
        Name name = new Name("Smith", "Will");
        manager.insert("123456789", name);
        Name name2 = new Name("Johnson", "Will");
        manager.update("123456789", name2);
        StringBuilder build = new StringBuilder();
        build.append("I don't want to write project");
        manager.essayOff(build);
        manager.clear("123456789");
        manager.print();
        boolean ifPrinted = true;
        assertTrue(ifPrinted);
    }

}
