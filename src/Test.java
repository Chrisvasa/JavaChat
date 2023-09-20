import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> userList = new ArrayList<>();

        // Creating the Frame
        JFrame frame = new JFrame("Tjatt-JPT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 240);

        // Creating the MenuBar and adding components
        JMenuBar menuBar = new JMenuBar();
        JMenu loginMenu = new JMenu("Login");
        JMenu userMenu = new JMenu("User");
        menuBar.add(loginMenu);
        menuBar.add(userMenu);
        JMenuItem edit = new JMenuItem("Edit User");
        JMenuItem logout = new JMenuItem("Logout");
        userMenu.add(edit);
        userMenu.add(logout);

        // Creating the panel at bottom and adding components
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10);
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Dialog", Font.PLAIN, 20));

        // Adding Components to the Frame
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.setVisible(true);

        // Popup menu for login in as a user
        loginMenu.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JPopupMenu loginMenuPop = new JPopupMenu();
                loginMenuPop.setVisible(true);
                loginMenuPop.add(new JMenuItem(new AbstractAction("Login") {
                    public void actionPerformed(ActionEvent e) {
                        userList.add(JOptionPane.showInputDialog("Username:"));
                        loginMenu.setText(userList.get(0));
                        loginMenuPop.setVisible(false);
                    }
                }));
                loginMenuPop.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        logout.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                loginMenu.setText("Login");
                userList.remove(0);
            }
        });

        // Sending message
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tf.getText().isBlank() && userList.size() > 0) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    textArea.setText(
                            textArea.getText() + userList.get(0) + " [" + LocalDateTime.now().format(formatter) + "]: "
                                    + tf.getText()
                                    + "\n");
                    tf.setText("");
                }
            }
        });

        // Reset for chat
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
    }
}
