package server;
import java.net.Socket;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    // Method to send messages to the client
    public void sendMessage(String message) {
        out.println(message);
    }

    // Method to receive messages from the client
    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void run() {
        try {
            while (true) {
                String message = receiveMessage();
                if (message != null) {
                    System.out.println("Received message: " + message);
                }
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
