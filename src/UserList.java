import java.util.ArrayList;

/*
 * A class with the purpose to handle all the users by having them inside an ArrayList
 * Has methods to add and remove users.
 * Also has method to print out all the user information.
 */
public class UserList {
    private ArrayList<User> userList = new ArrayList<>(); // List that stores all users
    private User user; // Stores the currently selected user

    public Boolean addUser(User user) {
        if (!userList.contains(user)) {
            if (checkAvailability(user.getUsername())) {
                userList.add(user);
                return true;
            }
        }
        return false;
    }

    // Checks if the username and password combo matches up
    public Boolean userLogin(String uName, String pWord) {
        for (User user : userList) {
            if (user.isValidUser(uName, pWord)) {
                return true;
            }
        }
        return false;
    }

    public String[] usersOnline() {
        StringBuilder sb = new StringBuilder();
        for (User user : userList) {
            if (user.checkOnline()) {
                sb.append(user.getUsername() + "[On]" + ",");
            } else {
                sb.append(user.getUsername() + "[Off]" + ",");
            }
        }
        return sb.toString().split(",");
    }

    private Boolean checkAvailability(String username) {
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    public Boolean removeUser(User user) {
        if (!userList.contains(user)) {
            userList.remove(user);
            return true;
        }
        return false;
    }

    public Boolean removeUser(String username) {
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                userList.remove(user);
                return true;
            }
        }
        return false;
    }

    private void selectUser(int index) {
        user = userList.get(index);
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getList() {
        return userList;
    }

    public void printUserInfo() {
        for (User user : userList) {
            user.PrintUser();
            System.out.println();
        }
    }
}
