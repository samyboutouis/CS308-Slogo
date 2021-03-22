package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class VariablesDisplayTest extends DukeApplicationTest {
  private final static ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;
  private ImageView turtle;

  @Override
  public void start (Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);
    addTurtle();
    addButtonView();
  }

  @Test
  void testUp() {
    turtle = lookup("#" + ID_BUNDLE.getString("Turtle")).query();
    double originalPosition = turtle.getTranslateY();
    Button upButton = lookup("#" + ID_BUNDLE.getString("UpButton")).query();
    TextField textField = lookup("#TextFieldUpButton").query();
    writeTo(textField, "90");
    clickOn(upButton);
    assertEquals(-90, turtle.getTranslateY() - originalPosition);
  }

  @Test
  void testDown() {
    turtle = lookup("#" + ID_BUNDLE.getString("Turtle")).query();
    double originalPosition = turtle.getTranslateY();
    Button downButton = lookup("#" + ID_BUNDLE.getString("DownButton")).query();
    TextField textField = lookup("#TextFieldDownButton").query();
    writeTo(textField, "90");
    clickOn(downButton);
    assertEquals(90, turtle.getTranslateY() - originalPosition);
  }

  @Test
  void testRight() {
    turtle = lookup("#" + ID_BUNDLE.getString("Turtle")).query();
    double originalDirection = turtle.getRotate();
    Button rightButton = lookup("#" + ID_BUNDLE.getString("RightButton")).query();
    TextField textField = lookup("#TextFieldRightButton").query();
    writeTo(textField, "90");
    clickOn(rightButton);
    assertEquals(90, turtle.getRotate() - originalDirection);
  }

  @Test
  void testLeft() {
    turtle = lookup("#" + ID_BUNDLE.getString("Turtle")).query();
    double originalDirection = turtle.getRotate();
    Button rightButton = lookup("#" + ID_BUNDLE.getString("LeftButton")).query();
    TextField textField = lookup("#TextFieldLeftButton").query();
    writeTo(textField, "90");
    clickOn(rightButton);
    assertEquals(-90, turtle.getRotate() - originalDirection);
  }

  private void addTurtle() {
    Button addTurtleButton = lookup(String.format("%s%s", "#", ID_BUNDLE.getString("AddTurtleButton"))).query();
    clickOn(addTurtleButton);
  }

  private void addButtonView() {
    ComboBox<String> comboBox = lookup("#ViewContainerComboBox").query();
    select(comboBox, "Move Turtle");
  }
}
