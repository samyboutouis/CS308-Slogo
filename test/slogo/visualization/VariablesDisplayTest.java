package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class VariablesDisplayTest extends DukeApplicationTest {
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private TextArea terminalTextArea;
  private VBox variablesBox;
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
    select(comboBox, "Variables");
  }

  @Test
  void testCreateVariable(){
    variablesBox = lookup("#VariablesBox").query();
    writeTo(terminalTextArea, "set :a 100");
    clickOn(runButton);
    assertEquals(1, variablesBox.getChildren().size());
  }
}
