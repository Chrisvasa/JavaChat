package client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.DataAccess;

public class LoginListener implements ActionListener {
    private JTextField userInput;
    private JPasswordField pass;
    private JFrame frame;
    private JPanel loginPanel;
    private Client client;

    public LoginListener(JTextField userInput, JPasswordField pass, JFrame frame, JPanel loginPanel) {
        this.userInput = userInput;
        this.pass = pass;
        this.frame = frame;
        this.loginPanel = loginPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Login") {
            handleLogin();
        }
    }

    private void handleLogin() {
        String username = userInput.getText();
        String password = new String(pass.getPassword());

        try {
            if (DataAccess.verifyLogin(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                Chat chat = new Chat(client);
                chat.render();
                frame.setVisible(false);
                initializeUser(username);

                // Remove login panel and show new components
                loginPanel.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        } catch (HeadlessException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    // Creates a socket unique for the user,
    // so that they can be identified on the server.
    // Also assigns them a username (Can also set the IP adress of the server here)
    private void initializeUser(String userName) throws SQLException {
        try {
            Socket socket = new Socket("localhost", 6969);
            client = new Client(socket, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
