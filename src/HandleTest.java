import student.TestCase;

/**
 * This class tests all the method in Handle class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12/5/2019
 */
public class HandleTest extends TestCase {
    private Handle handle1;


    /**
     * Set up for the test
     */
    public void setUp() {
        handle1 = new Handle(500, 700);
    }


    /**
     * Test the setStart and getStart method
     */
    public void testStart() {
        handle1.setStart(300);
        assertEquals(300, handle1.getStart());
    }


    /**
     * Test the setLength and getLength method
     */
    public void testLength() {
        handle1.setLength(800);
        assertEquals(800, handle1.getLength());
    }

}
