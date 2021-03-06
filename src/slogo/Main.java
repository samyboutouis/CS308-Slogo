package slogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        createNewWindow();
    }

    /**
     * Sets up the new window by initializing the primary scene and the top-level root node
     */
    public void createNewWindow(){
        final String STAGE_TITLE = "SLogo Application";

        Pane root = new Pane();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(STAGE_TITLE);
        stage.setMaximized(true);

        stage.show();
    }
}
