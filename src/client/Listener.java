package client;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.DataAccess;

public class Listener implements ActionListener {
    private JTextField userInput;
    private JPasswordField pass;
    private JFrame frame;
    private JTextArea textArea;
    private JPanel loginPanel;
    private JPanel chatPanel;
    private JPanel peoplePanel;
    private JTextField message;
    private static Client client;

    public Listener(JTextField userInput, JPasswordField pass, JFrame frame, JTextArea textArea, JPanel loginPanel,
            JPanel chatPanel, JPanel peoplePanel, JTextField message) {
        this.userInput = userInput;
        this.pass = pass;
        this.frame = frame;
        this.textArea = textArea;
        this.loginPanel = loginPanel;
        this.chatPanel = chatPanel;
        this.peoplePanel = peoplePanel;
        this.message = message;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Login") {
            String username = userInput.getText();
            String password = new String(pass.getPassword());

            try {
                if (DataAccess.verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    initializeUser(username);
                    listenForMessages(textArea);

                    // Remove login panel and show new components
                    loginPanel.setVisible(false);
                    frame.getContentPane().add(BorderLayout.SOUTH, chatPanel);
                    frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(textArea));
                    frame.getContentPane().add(BorderLayout.EAST, peoplePanel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            // peoplePanel.repaint();
            // people.repaint();
        } else if (e.getActionCommand() == "Send") {
            if (!message.getText().isBlank()) {
                client.sendMessage(message.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String newMessage = "\n [" + LocalDateTime.now().format(formatter) + "] Me: "
                        + message.getText();
                textArea.append(newMessage);
                message.setText("");
            }
        }
    }

    // This will be waiting for a message that are
    // broadcasted broadcastMessage in ClientHandler
    // Each client will have a separate thread that is waiting for messages.
    private static void listenForMessages(JTextArea textArea) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader bufferedReader = client.getReader();
                while (true) {
                    try {
                        String msg = bufferedReader.readLine();
                        if (msg != null) {
                            textArea.append("\n" + msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // Creates a socket unique for the user,
    // so that they can be identified on the server.
    // Also assigns them a username (Can also set the IP adress of the server here)
    private static void initializeUser(String userName) throws SQLException {
        try {
            Socket socket = new Socket("localhost", 6969);
            client = new Client(socket, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
