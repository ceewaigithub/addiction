package server;

import java.net.Socket;
import java.io.*;

/*
    * The GameClient class is responsible for creating a client socket and sending and receiving messages from the server.
    * The client socket is created with the IP address and port number of the server.
    * The sendMessage method is used to send messages to the server, and the receiveMessage method is used to receive messages from the server.
    * The run method is used to handle the communication with the server in a separate thread.
*/

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
    /*
        * In the case of our game, the client will connect to the server and send the user's input to the server.
        * The server will process the input and send the game state back to the client.
        * The client will then update the game state and display it to the user by rendering the game in the gamePanels
        
        * The problem is that in the case of multiple card games, we might have to create multiple gameClients and gameServers
        * Because each game will have its own game state and rules and will need to be updated separately.
        * For example, in blackjack, the game state will include the player's hand, the dealer's hand, and the current bet.
        * In poker, the game state will include the player's hand, the community cards, and the current bet.
        * Each game will have its own set of rules and logic for updating the game state.
    */
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
