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

public class UserCommandsDisplayTest extends DukeApplicationTest {
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private TextArea terminalTextArea;
  private Button runButton;
  private VBox commandsBox;

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
    select(comboBox, "Commands");
  }

  @Test
  void testCreateCommand(){
    commandsBox = lookup("#UserCommandsBox").query();
    writeTo(terminalTextArea, "to cmd [ :a ] [ fd :a ]");
    clickOn(runButton);
    assertEquals(1, commandsBox.getChildren().size());
  }

  @Test
  void testCorrectCommandTag(){
    commandsBox = lookup("#UserCommandsBox").query();
    writeTo(terminalTextArea, "to cmd [ :a ] [ fd :a ]");
    clickOn(runButton);
    Button newTag = (Button) commandsBox.getChildren().get(0);
    assertEquals("cmd = [ :a ] [ fd :a ]", newTag.getText());
  }

  @Test
  void testCommandTagClick(){
    commandsBox = lookup("#UserCommandsBox").query();
    writeTo(terminalTextArea, "to cmd [ :a ] [ fd :a ]");
    clickOn(runButton);
    Button newTag = (Button) commandsBox.getChildren().get(0);
    clickOn(newTag);
    assertEquals("cmd = [ :a ] [ fd :a ]", newTag.getText());
  }
}
