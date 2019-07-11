import View.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {

    private static final String SERVER_IP = "172.20.9.231" ;
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            ServerConnection serverConnection = new ServerConnection(socket);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(serverConnection).start();


            Group group = new Group();
            Scene scene = new Scene(group, 1100, 700);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Viewer viewer = new Viewer(group, scene, keyboard, out);
            viewer.gameHandle();
//            while (true) {
//                String command = keyboard.readLine();
//                out.println(command);
//                if (command.equals("quit"))
//                    break;
//            }
        }catch ( Exception e ){
            Server server = new Server();
        }
    }
}
