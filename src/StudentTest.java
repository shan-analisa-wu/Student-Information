import student.TestCase;

/**
 * This class tests all the methods in the Student class
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12/5/2019
 */
public class StudentTest extends TestCase {
    private Student student1;
    private Handle name;
    private Handle essay;


    /**
     * Set up the test
     */
    public void setUp() {
        name = new Handle(30, 100);
        essay = new Handle(60, 300);
        student1 = new Student("12345", name, essay);
    }


    /**
     * Test the getPid method
     */
    public void testGetPid() {
        assertEquals("12345", student1.getPid());
        Student student2 = new Student("12345", name, essay);
        student2.getPid();
    }


    /**
     * Test the getNameHandle and setNameHandle method
     */
    public void testName() {
        Handle handle = new Handle(40, 200);
        student1.setNameHandle(handle);
        assertEquals(handle, student1.getNameHandle());
        Student student2 = new Student("12345", name, essay);
        student2.getPid();
    }


    /**
     * Test the getEssayHandle and setEssayHandle method
     */
    public void testEssay() {
        Handle handle = new Handle(40, 200);
        student1.setEssayHandle(handle);
        assertEquals(handle, student1.getEssayHandle());
        Student student2 = new Student("12345", name, essay);
        student2.getPid();
    }

}
