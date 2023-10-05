package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Chat {
    static Client client;

    public static void main(String[] args) throws UnknownHostException, IOException {
        // Generic UI design (Look and feel)
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("Button.arc", 0);
        UserList userList = new UserList();
        userList.addUser(new User("chrisvasa", "hej123", "chris@vasa.com"));
        userList.addUser(new User("MrToxic", "hej123", "cs2@now.com", true));
        userList.addUser(new User("GIGACHAD", "hej123", "Giga@chad.com", true));
        userList.addUser(new User("IfYouAsk", "YeShallRecieve", "LIAhunter@mail.com"));

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
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        // textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(UIManager.getColor("Label.background"));
        textArea.setBorder(UIManager.getBorder("Label.border"));

        // People list
        JPanel peoplePanel = new JPanel(new MigLayout("fill"));
        // Initialize the JList with a DefaultListModel
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> people = new JList(listModel);
        peoplePanel.add(people, "growy");

        // Adding components to the Window frame
        frame.getContentPane().add(BorderLayout.CENTER, loginPanel);
        frame.setVisible(true);

        // Listens to clicks on the login button
        // Then gets the input from the username and password field
        // and calls isValidUser method to check input
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userInput.getText();
                String password = new String(passInput.getPassword());

                if (userList.userLogin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    initializeUser(username);
                    listen(textArea);
                    // Update the user list model with the new online status
                    DefaultListModel<String> listModel = (DefaultListModel<String>) people.getModel();
                    listModel.clear(); // Clear the model

                    // Rebuild the model with updated user statuses
                    for (String userStatus : userList.usersOnline()) {
                        listModel.addElement(userStatus);
                    }

                    // Remove login panel and show new components
                    loginPanel.setVisible(false);
                    frame.getContentPane().add(BorderLayout.SOUTH, chatPanel);
                    frame.getContentPane().add(BorderLayout.CENTER, textArea);
                    frame.getContentPane().add(BorderLayout.EAST, peoplePanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
                // peoplePanel.repaint();
                // people.repaint();
            }
        });

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!message.getText().isBlank()) {
                    client.sendMessage(message.getText());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String newMessage = "\n [" + LocalDateTime.now().format(formatter) + "] Me: "
                            + message.getText()
                            + "\n";
                    textArea.append(newMessage);
                    message.setText("");
                }
            }
        });
    }

    // This will be waiting for a message that are
    // broadcasted broadcastMessage in ClientHandler
    // Each client will have a separate thread that is waiting for messages.
    public static void listen(JTextArea textArea) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bufferedReader = client.getReader();
                while (true) {
                    try {
                        String msg = bufferedReader.readLine();
                        if (msg != null) {
                            textArea.append(msg + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static void initializeUser(String userName) {
        try {
            Socket socket = new Socket("localhost", 6969);
            client = new Client(socket, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}