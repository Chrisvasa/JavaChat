package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatListener implements ActionListener {
    private JFrame chat;
    private JTextArea textArea;
    private JTextField message;
    private Client client;

    public ChatListener(JFrame chat, JTextArea textArea, JTextField message, Client client) {
        this.chat = chat;
        this.textArea = textArea;
        this.message = message;
        this.client = client;
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
        // loginPanel.setVisible(true);
        chat.setVisible(false);
        // frame.setVisible(true);
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

}
