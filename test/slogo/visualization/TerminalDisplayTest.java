package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class TerminalDisplayTest extends DukeApplicationTest {
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private TextArea terminalTextArea;
  private Button runButton;
  private Button saveButton;
  private Button loadButton;

  @Override
  public void start (Stage stage) {
    createWindow(stage);
    addTurtle();
  }

  private void createWindow(Stage stage){
    Pane root = new Pane();
    Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(scene);
    stage.show();
    new Workspace(root, scene, stage);

    terminalTextArea = lookup("#TerminalTextBox").query();
    runButton = lookup("#TerminalButton").query();
    saveButton = lookup("#TerminalSaveButton").query();
    loadButton = lookup("#TerminalLoadButton").query();
  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#AddTurtle").query();
    clickOn(addTurtleButton);
  }

  @Test
  void testSaveButton(){
    writeTo(terminalTextArea, "fd 50");
    clickOn(saveButton);
    assertEquals(1, 1);
  }

  @Test
  void testSaveButtonWhenEmpty(){
    clickOn(saveButton);
    assertEquals("", terminalTextArea.getText());
  }

  @Test
  void testLoadButton(){
    clickOn(loadButton);
    assertEquals("", terminalTextArea.getText());
  }

  @Test
  void testInvalidCommand(){
    writeTo(terminalTextArea, "fd a");
    clickOn(runButton);
    assertEquals("fd a", terminalTextArea.getText());
  }

  @Test
  void testMultipleTurtles(){
    writeTo(terminalTextArea, "tell [ 1 2 3 ] fd 100");
    clickOn(runButton);
    assertEquals("", terminalTextArea.getText());
  }
}
