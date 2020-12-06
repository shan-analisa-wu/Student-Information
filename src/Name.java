/**
 * Represents a person name that contains the first name, the last name, and the
 * middle name.
 * 
 * @author Shan Wu, Ko Yat Chan
 * @version 10.16.2019
 */
public class Name implements Comparable<Name> {
    /**
     * The first name of a person.
     */
    private String firstName;
    /**
     * The last name of a person.
     */
    private String lastName;

    /**
     * The middle name of a person
     */
    private String middleName;


    /**
     * Name constructor includes two parameters: firstName, lastName and
     * middleName.
     * 
     * @param firstName
     *            the first name.
     * @param lastName
     *            the last name.
     * @param middleName
     *            the middle name.
     */
    public Name(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }


    /**
     * A name constructor for students who do not have a middle name.
     * 
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * Getter for the first name.
     * 
     * @return firstName the first name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Setter for the last name.
     * 
     * @return lastName the last name of a person
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Setter for the middle name.
     * 
     * @return middleName the middle name of a person
     */
    public String getMiddleName() {
        return middleName;
    }


    /**
     * Setter for the first name
     * 
     * @param newFirstName
     *            a new first name
     * @return firstName a new first name
     */
    public String setFirstName(String newFirstName) {
        firstName = newFirstName;
        return firstName;
    }


    /**
     * Setter for the middle name.
     * 
     * @param newMiddleName
     *            a new middle name
     * @return middleName a new middle name
     */
    public String setMiddleName(String newMiddleName) {
        middleName = newMiddleName;
        return middleName;
    }


    /**
     * Setter for the last name.
     * 
     * @param newLastName
     *            a new last name
     * @return lastName a new last name
     */
    public String setLastName(String newLastName) {
        lastName = newLastName;
        return lastName;
    }


    /**
     * This method compares the name of two students.
     * Compare last name first, if the last name of two
     * students are the same then compare first name.
     * 
     * @param other
     *            the other student to compare
     * @return Return -1 if the name of the student precedes other
     *         student to be compared. Return 1 of the name of the
     *         student follows other student to be compared. Return
     *         0 if two names are equal.
     */
    public int compareTo(Name other) {
        if (this.getLastName().toLowerCase().compareTo(other.getLastName()
            .toLowerCase()) < 0) {
            return -1;
        }
        else if (this.getLastName().toLowerCase().compareTo(other.getLastName()
            .toLowerCase()) > 0) {
            return 1;
        }
        else { // 2: First name
            if (this.getFirstName().toLowerCase().compareTo(other.getFirstName()
                .toLowerCase()) < 0) {
                return -1;
            }
            else if (this.getFirstName().toLowerCase().compareTo(other
                .getFirstName().toLowerCase()) > 0) {
                return 1;
            }
            else {
                // they are equal
                return 0;
            }
        }
    }


    /**
     * Represents the output when I print Name:
     * The format is: "firstName lastName"
     * 
     * @return a printable result for the name object.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.firstName + " " + this.lastName);
        return str.toString();
    }
}
