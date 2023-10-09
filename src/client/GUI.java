package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class GUI {
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
        ActionListener actionListener = new Listener(userInput, passInput, frame, textArea, loginPanel, chatPanel,
                peoplePanel, message);
        loginButton.addActionListener(actionListener);
        send.addActionListener(actionListener);
    }
}