package server;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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