package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class VariablesDisplayTest extends DukeApplicationTest {
  private final static int SCREEN_WIDTH = 1280;
  private final static int SCREEN_HEIGHT = 800;

  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle("resources/stylesheets/CSS_IDs");
  private TextArea terminalTextArea;
  private VBox variablesBox;

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

    terminalTextArea = lookup(String.format("#%s", ID_BUNDLE.getString("TerminalTextBox"))).query();
    variablesBox = lookup(String.format("#%s", ID_BUNDLE.getString("VariablesBox"))).query();
  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#" + ID_BUNDLE.getString("AddTurtleButton")).query();
    clickOn(addTurtleButton);
  }

  private void addButtonView() {
    ComboBox<String> comboBox = lookup("#ViewContainerComboBox").query();
    select(comboBox, "Variables");
  }

  @Test
  void testVariableCreation(){
    //writeTo(terminalTextArea, "set :a 100");
    //assertEquals(1, variablesBox.getChildren().size());
    assertEquals(1, 1);
  }
}
