package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class TurtleDisplayTest extends DukeApplicationTest {
  private final static String RESOURCE_PACKAGE = "resources";
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private ResourceBundle idBundle;
  private ImageView turtle;

  @Override
  public void start(Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);
    idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    addTurtle();
  }

  @Test
  void testTurtleLoad() {
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
    assertTrue(turtle.isVisible());
  }

  @Test
  void testMoveForward() {
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
    double originalX = turtle.getTranslateX();
    double originalY = turtle.getTranslateY();
    runCommand("fd 50");
    assertEquals(-50, turtle.getTranslateY() - originalY);
    assertEquals(0, turtle.getTranslateX() - originalX);
  }

  @Test
  void testRotate() {
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
    double originalDirection = turtle.getRotate();
    runCommand("rt 50");
    assertEquals(50, turtle.getRotate() - originalDirection);
  }

  @Test
  void testActiveToggle() {
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
    ImageView activeCircle = lookup("#" + idBundle.getString("ActiveCircleID")).query();
    clickOn(turtle, (int) turtle.getTranslateX(), (int) turtle.getTranslateY());
    assertFalse(activeCircle.isVisible());
    clickOn(turtle, (int) turtle.getTranslateX(), (int) turtle.getTranslateY());
    assertTrue(activeCircle.isVisible());
  }

  @Test
  void testClearScreen() {
    runCommand("cs");
  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#" + idBundle.getString("AddTurtleButton")).query();
    clickOn(addTurtleButton);
  }

  private void runCommand(String command) {
    TextArea terminalTextBox = lookup("#" + idBundle.getString("TerminalTextBoxID")).query();
    Button terminalButton =  lookup("#" + idBundle.getString("TerminalButtonID")).query();
    writeTo(terminalTextBox, command);
    clickOn(terminalButton);
  }
}
