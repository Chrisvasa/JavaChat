import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class Chat {
    public static void main(String[] args) {
        // Initialize users and add them to an ArrayList<User>
        User chris = new User("chrisvasa", "hej123");
        User leif = new User("leif", "hej123");
        User toxic = new User("MrToxic", "hej123");
        ArrayList<User> userList = new ArrayList<>(Arrays.asList(chris, leif, toxic));

        // Creates the Window fram and sets the size
        JFrame frame = new JFrame("Tjatt-JPT 2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 640);
        frame.setLocationRelativeTo(null);

        // Create login component
        JPanel loginPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        // Sets size of input fields
        user.setColumns(12);
        pass.setColumns(12);
        // Adds the login components to the login panel
        loginPanel.add(user);
        loginPanel.add(pass);
        loginPanel.add(loginButton);

        // Adding components to the Window frame
        frame.getContentPane().add(BorderLayout.CENTER, loginPanel);
        frame.setVisible(true);

        // Listens to clicks on the login button
        // Then gets the input from the username and password field
        // and calls isValidUser method to check input
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                String password = new String(pass.getPassword());

                if (isValidUser(username, password, userList)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    user.setText("");
                    pass.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            }
        });
    }

    // Checks if the correct user/password combo was given and then returns
    // true or false
    private static boolean isValidUser(String user, String pass, ArrayList<User> userList) {
        for (User u : userList) {
            if (u._username.equalsIgnoreCase(user) && u._password.equalsIgnoreCase(pass)) {
                u.isOnline = true;
                return true;
            }
        }
        return false;
    }
}

// User class that keeps track of online activity
// And username / password
class User {
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
