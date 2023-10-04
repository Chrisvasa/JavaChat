package client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;
        int portNumber = 4444;
        try {
            socket = new Socket("localhost", portNumber);
            Thread.sleep(1000);
            Thread server = new Thread(new ServerThread(socket, "test"));
            server.start();
        } catch (IOException e) {
            System.err.println("Fatal connection error!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Fatal Connection error!");
            e.printStackTrace();
        }

    }
}
