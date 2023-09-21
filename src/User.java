// User class that keeps track of online activity
// And username / password
public class User {
    String _username;
    String _password;
    boolean isOnline = false;

    public User(String username, String password) {
        _username = username;
        _password = password;
    }

    public String getUserName() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }
}