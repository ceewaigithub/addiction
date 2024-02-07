package server;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 
 * The GameServer class is responsible for creating a server socket and listening for incoming client connections.
 * When a client connects, the server creates a new ClientHandler object to handle the client's requests.
 * 
*/

public class GameServer {
    private ServerSocket serverSocket;
    private static final int PORT = 8080; // port number where we are hosting the server
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                
            }
        } finally {
            serverSocket.close();
        }
    }

    /*
        When a client connects, the server creates a new ClientHandler object to handle the client's requests.
        The ClientHandler object is added to the list of clients and is executed in a separate thread.
        The server listens for incoming client connections in an infinite loop.

        *In the case of our game, the server will listen for incoming connections from the game clients.*
    */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            System.out.println("Waiting for a client to connect...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
            clients.add(clientHandler);
            pool.execute(clientHandler);
        }
    }
}