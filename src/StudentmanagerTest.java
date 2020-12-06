import java.io.IOException;
import student.TestCase;

/**
 * Tests the Studentmanager class and the FileParser class.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12.9.2019
 */
public class StudentmanagerTest extends TestCase {

    /**
     * Test the main method in Studentmanager class.
     * 
     * @throws IOException
     *             Throw exception if problem occur
     *             while reading file
     */
    public void testMain() throws IOException {
        Studentmanager manager = new Studentmanager();
        assertNotNull(manager);
        String[] input = new String[4];
        input[0] = "SampleInput.txt";
        input[2] = "64";
        input[3] = "test.data";
        Studentmanager.main(input);
    }

}
