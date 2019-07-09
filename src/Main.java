import View.Viewer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group, 1100, 700);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Viewer viewer = new Viewer(group, scene);
        viewer.gameHandle();
    }
}
