import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Chat {
    public static void main(String[] args) {
        // Generic UI design (Look and feel)
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("Button.arc", 0);

        // Initialize users and add them to an ArrayList<User>
        User chris = new User("chrisvasa", "hej123");
        User leif = new User("leif", "hej123");
        User toxic = new User("MrToxic", "hej123");
        ArrayList<User> userList = new ArrayList<>(Arrays.asList(chris, leif, toxic));

        // Creates the Window fram and sets the size
        JFrame frame = new JFrame("Tjatt-JPT 2.0");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 480);
        frame.setLocationRelativeTo(null);

        // Create login component
        JPanel loginPanel = new JPanel(new MigLayout("alignx center, aligny center, gapx 10px, gapy 10px"));
        JButton loginButton = new JButton("Login");
        JLabel userLabel = new JLabel("Username");
        JTextField userInput = new JTextField(10);
        JLabel passLabel = new JLabel("Password");
        JPasswordField passInput = new JPasswordField(10);
        // Sets size of input fields
        userInput.setColumns(12);
        passInput.setColumns(12);
        // Adds the login components to the login panel
        loginPanel.add(userLabel);
        loginPanel.add(userInput, "growx, wrap");
        loginPanel.add(passLabel);
        loginPanel.add(passInput, "growx, wrap");
        loginPanel.add(loginButton, "span 2, growx, alignx center");

        // Chat component
        JPanel chatPanel = new JPanel();
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        JTextField message = new JTextField();
        message.setColumns(12);
        // Add the components to the chat panel
        chatPanel.add(message);
        chatPanel.add(send);
        chatPanel.add(reset);

        // Text Area at the Center
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Dialog", Font.PLAIN, 16));

        // People list
        JPanel peoplePanel = new JPanel();
        String[] users = usersOnline(userList);
        JList people = new JList(users);
        peoplePanel.add(people);

        // Adding components to the Window frame
        frame.getContentPane().add(BorderLayout.CENTER, loginPanel);
        frame.setVisible(true);

        StringBuilder logged = new StringBuilder(); // TEMP - To keep track of logged in user
        // Listens to clicks on the login button
        // Then gets the input from the username and password field
        // and calls isValidUser method to check input
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userInput.getText();
                String password = new String(passInput.getPassword());

                if (isValidUser(username, password, userList)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    logged.append(username);
                    userInput.setText("");
                    passInput.setText("");
                    // Remove login panel and show new components
                    loginPanel.setVisible(false);
                    frame.getContentPane().add(BorderLayout.SOUTH, chatPanel);
                    frame.getContentPane().add(BorderLayout.CENTER, textArea);
                    frame.getContentPane().add(BorderLayout.EAST, peoplePanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            }
        });
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!message.getText().isBlank()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    textArea.setText(
                            textArea.getText() + logged + " [" + LocalDateTime.now().format(formatter) + "]: "
                                    + message.getText()
                                    + "\n");
                    message.setText("");
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

    private static String[] usersOnline(ArrayList<User> userList) {
        StringBuilder sb = new StringBuilder();
        for (User user : userList) {
            sb.append(user._username + ",");
        }
        return sb.toString().split(",");
    }
}