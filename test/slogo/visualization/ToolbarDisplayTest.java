package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ToolbarDisplayTest extends DukeApplicationTest {
  public static final String LANGUAGE = "English";
  private final static String RESOURCE_PACKAGE = "resources";
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private Workspace workspace;
  private ResourceBundle idBundle;
  private Button myColorThemeButton;
  private ColorPicker myBackgroundColorPicker;
  private ComboBox<String> myLanguageDropdown;
  private ComboBox<String> myHelpDropdown;
  private Pane myTurtlePane;
  private Button myNewButton;
  private Button myLoadButton;
  private Button mySaveButton;

  @Override
  public void start (Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    workspace = new Workspace(root, scene, stage);
    idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    myColorThemeButton = lookup("#" + idBundle.getString("ColorThemeButton")).query();
    myBackgroundColorPicker = lookup("#" + idBundle.getString("ColorPicker")).query();
    myLanguageDropdown = lookup("#" + idBundle.getString("LanguageDropdown")).query();
    myNewButton = lookup("#" + idBundle.getString("NewWorkspaceButton")).query();
    myLoadButton = lookup("#" + idBundle.getString("LoadWorkspaceButton")).query();
    mySaveButton = lookup("#" + idBundle.getString("SaveWorkspaceButton")).query();
    myHelpDropdown = lookup("#" + idBundle.getString("HelpDropdown")).query();
    myTurtlePane = lookup("#" + idBundle.getString("TurtlePaneID")).query();
    addTurtle();
  }

  @Test
  void testAddTurtle() {
    addTurtle();
    Node turtle = lookup("#" + idBundle.getString("Turtle")).query();
  }

  @Test
  void testChangeColorTheme() {
    assertTrue(myColorThemeButton.isVisible());
    workspace.getStylesheet().equals("dark.css");
    clickOn(myColorThemeButton);
    workspace.getStylesheet().equals("light.css");
  }

  @Test
  void testChangeBackgroundColor() {
    assertTrue(myBackgroundColorPicker.isVisible());
    setValue(myBackgroundColorPicker, Color.RED);
    Color backgroundColor = (Color) myTurtlePane.getBackground().getFills().get(0).getFill();
    assertEquals(backgroundColor, Color.RED);
  }

  @Test
  void testChangeLanguage() {
    assertTrue(myLanguageDropdown.isVisible());
    assertEquals(myLanguageDropdown.getValue(), LANGUAGE);
    select(myLanguageDropdown, "Italiano");
    assertEquals(myLanguageDropdown.getValue(), "Italiano");
    String input = "ripitere 6 [ ripitere 2 [ ripitere 30 [ ava 6 des 2 ] des 120 ] des 60 ]";
    runCommand(input);
  }

  @Test
  void testNewWorkspace() {
    assertTrue(myNewButton.isVisible());
    clickOn(myNewButton);
  }

  @Test
  void testSaveWorkspace() {
    assertTrue(mySaveButton.isVisible());
    clickOn(mySaveButton);
  }

  @Test
  void testSelectHelp() {
    assertTrue(myHelpDropdown.isVisible());
    select(myHelpDropdown, "atan");
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