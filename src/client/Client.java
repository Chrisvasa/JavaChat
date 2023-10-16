package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import javax.swing.JTextArea;
import data.DataAccess;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private boolean isRunning;
    private ChatListener chat;

    public Client(Socket socket, String username, ChatListener chat) {
        try {
            this.socket = socket;
            this.username = username;
            this.chat = chat;
            this.isRunning = true;
            System.out.println(username);
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // Send messages to our clienthandler
    public void sendMessage(String messageToSend) {
        try {
            if (socket.isConnected()) {
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public BufferedReader getReader() {
        return bufferedReader;
    }

    public void closeSocket() {
        try {
            setRunning(false);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRunning(boolean setValue) {
        isRunning = setValue;
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This will be waiting for a message that are
    // broadcasted broadcastMessage in ClientHandler
    // Each client will have a separate thread that is waiting for messages.
    public void listenForMessages(JTextArea textArea) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        String msg = bufferedReader.readLine();
                        if (msg.contains("entered") || msg.contains("has left")) {
                            chat.addUsersToList(DataAccess.getOnlineUsers(username));
                        }
                        if (msg != null) {
                            textArea.append("\n" + msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }).start();
    }
}
