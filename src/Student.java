/**
 * This class represents a student
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.20.2019
 */
public class Student {
    private Handle nameHandle;
    private String pid;
    private Handle essayHandle;


    /**
     * Create a new student object
     * 
     * @param name
     *            the name of the student
     * @param pid
     *            the pid of the student
     * @param essay
     *            the essay of the student
     */
    public Student(String pid, Handle name, Handle essay) {
        this.nameHandle = name;
        this.essayHandle = essay;
        this.pid = pid;
    }


    /**
     * This method get the name handle of a student
     * 
     * @return nameHandle The nameHandle of a student
     */
    public Handle getNameHandle() {
        return nameHandle;
    }


    /**
     * This method get the essay handle of a student
     * 
     * @return essayHandle The essayHandle of a student
     */
    public Handle getEssayHandle() {
        return essayHandle;
    }


    /**
     * This command returns the student id of a student
     * 
     * @return pid The pid of a student
     */
    public String getPid() {
        return pid;
    }


    /**
     * This command set the name handle of the student
     * 
     * @param name
     *            The name handle
     */
    public void setNameHandle(Handle name) {
        this.nameHandle = name;
    }


    /**
     * This command set the essay handle of the student
     * 
     * @param essay
     *            The essay handle
     */
    public void setEssayHandle(Handle essay) {
        this.essayHandle = essay;
    }

}
