package client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Login {

    public static void main(String[] args) {
        // Generic UI design (Look and feel)
        FlatLightLaf.setup();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("Button.arc", 0);

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

        // Adding components to the Window frame
        frame.getContentPane().add(BorderLayout.CENTER, loginPanel);
        frame.setVisible(true);

        ActionListener actionListener = new LoginListener(userInput, passInput, frame, loginPanel);
        loginButton.addActionListener(actionListener);
    }
}