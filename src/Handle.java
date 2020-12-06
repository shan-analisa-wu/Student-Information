/**
 * This class represent the Memory Handle
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 12/3/2019
 *
 */
public class Handle {
    private long start;
    private long length;


    /**
     * Create a new Handle object
     * 
     * @param start
     *            the start of the record
     * @param length
     *            the length of the record
     */
    public Handle(long start, long length) {
        this.start = start;
        this.length = length;
    }


    /**
     * This method set the start of a handle
     * 
     * @param start
     *            The start of a handle
     */
    public void setStart(long start) {
        this.start = start;
    }


    /**
     * This method set the length of bytes of
     * a handle
     * 
     * @param length
     *            the length of a handle
     */
    public void setLength(long length) {
        this.length = length;
    }


    /**
     * This method returns the the start of bytes of
     * the essay handle
     * 
     * @return The start of bytes of the essay handle
     */
    public long getStart() {
        return start;
    }


    /**
     * This method returns the the length of bytes of
     * the essay handle
     * 
     * @return The length of bytes of the essay handle
     */
    public long getLength() {
        return length;
    }

}
