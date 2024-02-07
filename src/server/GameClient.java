package server;

import java.net.Socket;
import java.io.*;

public class GameClient {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public GameClient(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    // Method to send messages to the server
    public void sendMessage(String message) {
        out.println(message);
    }

    // Method to receive messages from the server
    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void run() {
        try {
            while (true) {
                String message = receiveMessage();
                System.out.println("Received message: " + message);
                sendMessage("Message received: " + message);
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing the client socket: " + e.getMessage());
            }
        }
    }
}
