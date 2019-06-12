import View.*;
import Controller.*;
import Modules.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group, 1000, 1000);
        //stage.setScene(scene);
        //Button button = new Button();
       // stage.show();
        Viewer viewer = new Viewer(group, scene);
        viewer.gameHandle();
    }
}
