package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import client.Client;

// GUIDE:
// https://www.instructables.com/Creating-a-Chat-Server-Using-Java/
public class ChatServer {
    static ServerSocket serverSocket;
    public static void main(String[] args) {
        serverSocket = null;
        System.out.println("Please input username");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        scan.close();
        int portNumber = 4444;
        acceptClients(portNumber);
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch(IOException e) {
            System.err.println("Could not listen to the port dumbfuck: " + portNumber);
            System.exit(1);
        }
    }

    public static void acceptClients(int portNumber) {
        ArrayList<ClientThread> clients = new ArrayList<>();
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException e) {
                System.out.println("Accept failed on: " + portNumber);
            }
        }
    }
}
