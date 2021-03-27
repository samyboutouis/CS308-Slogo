package slogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.visualization.Workspace;

/**
 * The Main class is responsible for starting the application window and
 * creating the main scene.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 */
public class Main extends Application {

  private final static int SCREEN_WIDTH = 1600;
  private final static int SCREEN_HEIGHT = 900;
  private final static String STAGE_TITLE = "SLogo Application";

  /**
   * Starts the window application
   * @param stage The top-level container class for the application window
   */
  @Override
  public void start(Stage stage) {
    createNewWorkspace();
  }

  /**
   * Sets up a new window by initializing the main scene, the root node,
   * and the Workspace object of the stage.
   */
  public void createNewWorkspace() {
    Stage stage = new Stage();

    stage.setTitle(STAGE_TITLE);

    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);
  }
}
