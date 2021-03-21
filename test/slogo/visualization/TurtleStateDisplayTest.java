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

public class TurtleStateDisplayTest extends DukeApplicationTest {
  private final static String RESOURCE_PACKAGE = "resources";

  private ResourceBundle idBundle;
  private ImageView turtle;

  @Override
  public void start(Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root);
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

//  @Test
//  void testChangePenColor() {
//    assertTrue(myPenColorButton.isVisible());
//    clickOn(myPenColorButton);
//    assertTrue(myPenColorButton.isDisabled());
//    assertFalse(myPenColorButton.isVisible());
//    ColorPicker colorPicker = lookup("#" + idBundle.getString("ColorPicker")).query();
//    setValue(colorPicker, Color.RED);
//    TextArea terminalTextBox = lookup("#" + idBundle.getString("TerminalTextBoxID")).query();
//    Button terminalButton =  lookup("#" + idBundle.getString("TerminalButtonID")).query();
//    writeTo(terminalTextBox, "fd 50");
//    clickOn(terminalButton);
//    Line line = lookup("#" + idBundle.getString("LineID")).query();
//    Color lineColor = (Color) line.getStroke();
//    assertEquals(lineColor, Color.RED);
//  }

//  @Test
//  void testChangeTurtleImage() {
//    ImageView turtleImageView = lookup("#" + idBundle.getString("Turtle")).query();
//    assertTrue(myTurtleImageButton.isVisible());
//    clickOn(myTurtleImageButton);
//  }

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