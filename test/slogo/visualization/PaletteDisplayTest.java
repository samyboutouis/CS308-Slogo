package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PaletteDisplayTest extends DukeApplicationTest {
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private TextArea terminalTextArea;
  private VBox paletteBox;
  private Button runButton;

  @Override
  public void start (Stage stage) {
    createWindow(stage);
    addTurtle();
    addButtonView();
  }

  private void createWindow(Stage stage){
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);

    terminalTextArea = lookup("#TerminalTextBox").query();
    runButton = lookup("#TerminalButton").query();
  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#AddTurtle").query();
    clickOn(addTurtleButton);
  }

  private void addButtonView() {
    ComboBox<String> comboBox = lookup("#ViewContainerComboBox").query();
    select(comboBox, "Palette");
  }

  @Test
  void testCreatePalette(){
    paletteBox = lookup("#PaletteBox").query();
    writeTo(terminalTextArea, "setpalette 7 150 150 150");
    clickOn(runButton);
    assertEquals(7, paletteBox.getChildren().size());
  }

  @Test
  void testUpdateExistingPalette(){
    paletteBox = lookup("#PaletteBox").query();
    writeTo(terminalTextArea, "setpalette 7 150 150 150");
    clickOn(runButton);
    writeTo(terminalTextArea, "setpalette 7 0 0 0");
    clickOn(runButton);
    assertEquals(7, paletteBox.getChildren().size());
  }

  @Test
  void testSetShape(){
    paletteBox = lookup("#PaletteBox").query();
    writeTo(terminalTextArea, "setshape 2");
    clickOn(runButton);
    assertEquals(6, paletteBox.getChildren().size());
  }

  @Test
  void testSetBackground(){
    paletteBox = lookup("#PaletteBox").query();
    writeTo(terminalTextArea, "setbackground 4");
    clickOn(runButton);
    assertEquals(6, paletteBox.getChildren().size());
  }
}
