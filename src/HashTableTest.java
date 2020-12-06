import student.TestCase;

/**
 * Tests all the functionalities of a hash table.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12.03.2019
 */
public class HashTableTest extends TestCase {
    /**
     * The hash table we use in testing.
     */
    private HashTable hash;
    /**
     * pid for testing.
     */
    private int pid = 22332;

    /**
     * Setup for testing.
     */
    public void setUp() {
        hash = new HashTable(64);
    }


    /**
     * main testing class.
     */
    public void test() {
        for (int i = 0; i < 200; i++) {
            hash.insert(new Student(Integer.toString(pid + i), new Handle(0, 0),
                new Handle(0, 0)));
        }
        assertEquals(-1, hash.insert(new Student(Integer.toString(pid),
            new Handle(0, 0), new Handle(0, 0))));
        assertEquals(hash.remove(Integer.toString(22332)), 1);
        assertEquals(hash.getHashSize(), 64);
        assertEquals(hash.getNumOfBuckets(), 2);
        assertEquals(1, hash.insert(new Student(Integer.toString(pid),
            new Handle(0, 0), new Handle(0, 0))));
        assertEquals(hash.find("22332").getPid(), new Student("22332",
            new Handle(0, 0), new Handle(0, 0)).getPid());
    }
}
