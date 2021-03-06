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
     * Starts the game window application
     * @param stage The window application that displays the simulation
     */
    @Override
    public void start(Stage stage) {
        createNewWindow();
    }

    /**
     * Sets up the new window by initializing the main scene and the root node
     */
    public void createNewWindow(){

        final String STAGE_TITLE = "SLogo Application";
        final double SCREEN_X = Screen.getPrimary().getVisualBounds().getMinX();
        final double SCREEN_Y = Screen.getPrimary().getVisualBounds().getMinY();
        final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
        final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

        Pane root = new Pane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle(STAGE_TITLE);
        stage.setX(SCREEN_X);
        stage.setY(SCREEN_Y);
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);

        stage.show();
    }
}
