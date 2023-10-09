package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Chat {
    private String username;
    private JFrame frame;

    public Chat(String username, JFrame frame) {
        this.username = username;
        this.frame = frame;
    }

    public void render() {
        // Chat panel
        JFrame chat = new JFrame("Tjatt-JPT 2.0");
        chat.setResizable(false);
        chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat.setSize(360, 480);
        chat.setLocationRelativeTo(frame);

        // Chat component
        JPanel chatPanel = new JPanel();
        JButton send = new JButton("Send");
        JButton reset = new JButton("Logout");
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

        // // People list
        // JPanel peoplePanel = new JPanel(new MigLayout("fill"));
        // // Initialize the JList with a DefaultListModel
        // DefaultListModel<String> listModel = new DefaultListModel<>();
        // JList<String> people = new JList(listModel);
        // peoplePanel.add(people, "growy");

        chat.getContentPane().add(BorderLayout.SOUTH, chatPanel);
        chat.getContentPane().add(BorderLayout.CENTER, new JScrollPane(textArea));
        // chat.getContentPane().add(BorderLayout.EAST, peoplePanel);
        chat.setVisible(true);

        ActionListener actionListener = new ChatListener(chat, textArea, message, username);
        send.addActionListener(actionListener);
        reset.addActionListener(actionListener);
    }

}
