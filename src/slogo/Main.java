package slogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.visualization.Workspace;

public class Main extends Application  {

    private final static int SCREEN_WIDTH = 1600;
    private final static int SCREEN_HEIGHT = 900;
    private final static String STAGE_TITLE = "SLogo Application";

    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Starts the window application
     * @param stage The top-level container class for the application window
     */
    @Override
    public void start(Stage stage) {
        createNewWorkspace();
    }

    public void createNewWorkspace(){
        Stage stage = new Stage();

        stage.setTitle(STAGE_TITLE);

        Pane root = new Pane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
        new Workspace(root, scene, stage);
    }
}
