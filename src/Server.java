import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 5000;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public Server() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        while ( true ) {
            System.out.println("server w8ing for client connection...");
            Socket client = listener.accept();
            System.out.println("server connected to client!");
            ClientHandler clientHandler = new ClientHandler(client, clients);
            clients.add(clientHandler);
            pool.execute(clientHandler);
        }

    }
}