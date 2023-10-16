package client;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.DataAccess;

public class LoginListener implements ActionListener {
    private JTextField userInput;
    private JPasswordField pass;
    private JFrame frame;

    public LoginListener(JTextField userInput, JPasswordField pass, JFrame frame) {
        this.userInput = userInput;
        this.pass = pass;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Login") {
            handleLogin();
        }
    }

    private void handleLogin() {
        String username = userInput.getText();
        try {
            if (DataAccess.verifyLogin(username, new String(pass.getPassword()))) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                Chat chat = new Chat(username, frame);
                chat.render();
                frame.setVisible(false);
                frame.dispose();
                chat.addUsersToList(DataAccess.getOnlineUsers(username));
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        } catch (HeadlessException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
