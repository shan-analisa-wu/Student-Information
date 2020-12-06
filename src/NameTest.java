import student.TestCase;

/**
 * Tests all the functionalities of the Name class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 09.20.2019
 *
 */
public class NameTest extends TestCase {
    /**
     * First name to be tested
     */
    private Name name1;
    /**
     * Second name to be tested
     */
    private Name name2;
    /**
     * Third name to be tested
     */
    private Name name3;
    /**
     * A string array contains all the dummy names we will use for testing.
     */
    private String[] names = { "Steven", "Johnson", "Lee", "Allen", "Josh",
        "Wallace" };


    /**
     * Setup for the following testings.
     */
    public void setUp() {
        name1 = new Name(names[0], names[1]);
        name2 = new Name(names[2], names[3]);
        name3 = new Name(names[4], names[5]);
    }


    /**
     * testing all the getters and setters.
     */
    public void testers() {
        // test getters
        assertEquals(names[0], name1.getFirstName());
        assertEquals(names[2], name2.getFirstName());
        assertEquals(names[4], name3.getFirstName());
        assertEquals(names[1], name1.getLastName());
        assertEquals(names[3], name2.getLastName());
        assertEquals(names[5], name3.getLastName());
        // test setters
        assertEquals(names[0], name3.setLastName(names[0]));
        assertEquals(names[1], name2.setLastName(names[1]));
        assertEquals(names[2], name1.setLastName(names[2]));
        assertEquals(names[3], name3.setFirstName(names[3]));
        assertEquals(names[4], name2.setFirstName(names[4]));
        assertEquals(names[5], name1.setFirstName(names[5]));
        assertEquals("Wallace Lee", name1.toString());
    }

}
