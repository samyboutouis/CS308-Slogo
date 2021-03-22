package slogo.visualization;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class TurtleStateDisplayTest extends DukeApplicationTest {
  private final static String RESOURCE_PACKAGE = "resources";
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private ResourceBundle idBundle;
  private ComboBox<Integer> turtleComboBox;
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
    addStateView();
  }

  @Test
  void testTurtleLoad() {
    selectTurtleView();
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
    assertTrue(turtle.isVisible());
  }

  @Test
  void testMoveForward() {
    selectTurtleView();
    runCommand("cs");
    runCommand("fd 50");
    Text yPosition = lookup("#YPosition").query();
    assertEquals(yPosition.getText(), "50.00");
  }

  @Test
  void testMoveBackward() {
    selectTurtleView();
    runCommand("cs");
    runCommand("bk 50");
    Text yPosition = lookup("#YPosition").query();
    assertEquals(yPosition.getText(), "-50.00");
  }

  @Test
  void testMoveRight() {
    selectTurtleView();
    runCommand("cs");
    runCommand("rt 90");
    Text direction = lookup("#Direction").query();
    assertEquals(direction.getText(), "90.00");
  }

  @Test
  void testChangePenColor() {
    selectTurtleView();
    runCommand("cs");
    ColorPicker colorPicker = lookup("#PenColorPicker").query();
    assertTrue(colorPicker.isVisible());
    setValue(colorPicker, Color.RED);
    runCommand("fd 50");
    Line line = lookup("#" + idBundle.getString("LineID")).query();
    Color lineColor = (Color) line.getStroke();
    assertEquals(lineColor, Color.RED);
  }

  @Test
  void testChangePenWidth() {
    selectTurtleView();
    runCommand("cs");
    Slider slider = lookup("#Slider").query();
    setValue(slider, 5.0);
    runCommand("fd 50");
    Line line = lookup("#" + idBundle.getString("LineID")).query();
    double lineWidth = line.getStrokeWidth();
    assertEquals(5.0, lineWidth);
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

  private void addStateView() {
    ComboBox<String> comboBox = lookup("#ViewContainerComboBox").query();
    select(comboBox, "Turtles");
  }

  private void selectTurtleView() {
    turtleComboBox = lookup("#TurtleDropdown").query();
    select(turtleComboBox, 1);
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        turtleComboBox.setValue(1);
      }
    });
  }
}