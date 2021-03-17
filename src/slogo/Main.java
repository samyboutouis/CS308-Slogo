package slogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.visualization.Workspace;

public class Main extends Application  {
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
        final String STAGE_TITLE = "SLogo Application";

        stage.setMaximized(true);
        stage.setTitle(STAGE_TITLE);

        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new Workspace(root, scene, stage);
    }
}
