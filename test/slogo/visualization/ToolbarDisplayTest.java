package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ToolbarDisplayTest extends DukeApplicationTest {
  public static final String LANGUAGE = "English";
  private final static String RESOURCE_PACKAGE = "resources";

  private ResourceBundle idBundle;
  private Button myPenColorButton;
  private Button myBackgroundColorButton;
  private Button myTurtleImageButton;
  private ComboBox<String> myLanguageDropdown;
  private ComboBox<String> myHelpDropdown;
  private Pane myTurtlePane;

  @Override
  public void start (Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);
    idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    myPenColorButton = lookup("#" + idBundle.getString("PenColorButton")).query();
    myBackgroundColorButton = lookup("#" + idBundle.getString("BackgroundColorButton")).query();
    myTurtleImageButton = lookup("#" + idBundle.getString("TurtleImageButton")).query();
    myLanguageDropdown = lookup("#" + idBundle.getString("LanguageDropdown")).query();
    myHelpDropdown = lookup("#" + idBundle.getString("HelpDropdown")).query();
    myTurtlePane = lookup("#" + idBundle.getString("TurtlePaneID")).query();
    addTurtle();
  }

  @Test
  void testChangePenColor() {
    assertTrue(myPenColorButton.isVisible());
    clickOn(myPenColorButton);
    assertTrue(myPenColorButton.isDisabled());
    assertFalse(myPenColorButton.isVisible());
    ColorPicker colorPicker = lookup("#" + idBundle.getString("ColorPicker")).query();
    setValue(colorPicker, Color.RED);
    TextArea terminalTextBox = lookup("#" + idBundle.getString("TerminalTextBoxID")).query();
    Button terminalButton =  lookup("#" + idBundle.getString("TerminalButtonID")).query();
    writeTo(terminalTextBox, "fd 50");
    clickOn(terminalButton);
    Line line = lookup("#" + idBundle.getString("LineID")).query();
    Color lineColor = (Color) line.getStroke();
    assertEquals(lineColor, Color.RED);
  }

  @Test
  void testChangeBackgroundColor() {
    assertTrue(myBackgroundColorButton.isVisible());
    clickOn(myBackgroundColorButton);
    assertTrue(myBackgroundColorButton.isDisabled());
    assertFalse(myBackgroundColorButton.isVisible());
    ColorPicker colorPicker = lookup("#" + idBundle.getString("ColorPicker")).query();
    setValue(colorPicker, Color.RED);
    Color backgroundColor = (Color) myTurtlePane.getBackground().getFills().get(0).getFill();
    assertEquals(backgroundColor, Color.RED);
  }

  @Test
  void testChangeTurtleImage() {
    ImageView turtleImageView = lookup("#" + idBundle.getString("Turtle")).query();
    assertTrue(myTurtleImageButton.isVisible());
    clickOn(myTurtleImageButton);
  }

  @Test
  void testChangeLanguage() {
    assertTrue(myLanguageDropdown.isVisible());
    assertEquals(myLanguageDropdown.getValue(), LANGUAGE);
//    select(myLanguageDropdown, "Français");
//    assertEquals(myLanguageDropdown.getValue(), "Français");
  }

  @Test
  void testSelectHelp() {
    assertTrue(myHelpDropdown.isVisible());
//    select(myLanguageDropdown, "atan");
  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#" + idBundle.getString("AddTurtleButton")).query();
    clickOn(addTurtleButton);
  }
}
