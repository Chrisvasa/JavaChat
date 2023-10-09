package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatListener implements ActionListener {
    private JFrame chat;
    private JTextArea textArea;
    private JTextField message;
    private String username;
    private Client client;

    public ChatListener(JFrame chat, JTextArea textArea, JTextField message, String username) {
        this.chat = chat;
        this.textArea = textArea;
        this.message = message;
        this.username = username;
        initializeUser();
        client.listenForMessages(textArea);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Send") {
            sendMessage();
        } else if (e.getActionCommand() == "Reset") {
            resetMessages();
        } else if (e.getActionCommand() == "Logout") {
            handleLogout();
        }
    }

    private void resetMessages() {
        textArea.setText("");
    }

    private void handleLogout() {
        // Implement Logout
        client.closeSocket();
        Login.setupLogin();
        chat.setVisible(false);
        chat.dispose();
    }

    private void sendMessage() {
        if (!message.getText().isBlank()) {
            client.sendMessage(message.getText());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String newMessage = "\n [" + LocalDateTime.now().format(formatter) + "] Me: "
                    + message.getText();
            textArea.append(newMessage);
            message.setText("");
        }
    }

    // Creates a socket unique for the user,
    // so that they can be identified on the server.
    // Also assigns them a username (Can also set the IP adress of the server here)
    private void initializeUser() {
        try {
            Socket socket = new Socket("localhost", 6969);
            client = new Client(socket, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
