package client;
import java.time.Year;

/*
 * User class that allows for the creation of a user with some basic information such as:
 * First & Last name, Username, Email, Password and Birthyear
 * Then allows for change on First & Last name and their password.
 * Also has methods to print out information about the user
 */
public class User {
    // Properties
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Year birthYear;
    private Boolean isOnline = false;

    // METHOD OVERLOADING
    // Basic constructor that fills in some of the data
    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = "foo";
        this.lastName = "bar";
        this.birthYear = Year.parse("1900");
    }

    public User(String userName, String password, String email, Boolean isOnline) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.isOnline = isOnline;
        this.firstName = "foo";
        this.lastName = "bar";
        this.birthYear = Year.parse("1900");
    }

    // Constructor for everything except the birth year
    public User(String userName, String password, String email, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        birthYear = Year.parse("1900");
    }

    // Constructor that takes in all input upon creation
    public User(String userName, String password, String email, String firstName, String lastName, String birthyear) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = Year.parse(birthyear);
    }

    public String getName() {
        return this.firstName;
    }

    public String getUsername() {
        return this.userName;
    }

    public Boolean setFirstName(String firstName) {
        if (firstName.length() > 0 && firstName.length() < 15) {
            this.firstName = firstName;
            return true;
        }
        return false;
    }

    public Boolean setLastName(String lastName) {
        if (lastName.length() > 0 && lastName.length() < 15) {
            this.lastName = lastName;
            return true;
        }
        return false;
    }

    public Year getBirthYear() {
        return this.birthYear;
    }

    public Boolean changePassowrd(String pass) {
        if (pass.length() < 15) {
            this.password = pass;
            return true;
        }
        return false;
    }

    public void PrintUser() {
        System.out.printf("INFORMATION ABOUT %s\n"
                + "Username: %s\n"
                + "Email: %s\n"
                + "Full name: %s %s\n"
                + "Born in year: %s\n"
                + "And they are %d years old.\n", firstName.toUpperCase(), userName, email, firstName, lastName,
                birthYear.toString(), getAge());
    }

    private Integer getAge() {
        return Year.now().getValue() - birthYear.getValue();
    }

    public Boolean checkOnline() {
        if (isOnline) {
            return true;
        }
        return false;
    }

    // Allows for user validation without giving out the password
    public Boolean isValidUser(String u, String p) {
        if (userName.equals(u) && password.equals(p)) {
            isOnline = true;
            return true;
        }
        return false;
    }

}
