package slogo.visualization.displays;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

/**
 * The VariablesDisplay class is responsible for creating an instance of the variables display
 * view, including all of its UI components, and managing the creation/modification of tags from
 * certain actions (e.g., when a new variable is created and set from the terminal).
 *
 * @author Donghan Park
 */
public class VariablesDisplay extends ScrollingDisplay {

  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final String VARIABLES_TITLE = "VariablesTitle";
  private static final String VARIABLES_BOX_ID = "VariablesBoxID";
  private static final String VARIABLES_TAG_ID = "VariablesTagID";
  private static final String DIALOG_BOX_HEADER_TEXT = "Set a new value for:";
  private static final String SET_COMMAND = "set :";

  private final VBox variablesBox;

  /**
   * Constructor that creates an instance of the VariablesDisplay object.
   * @param workspace Reference to the workspace object, which encapsulates all display views.
   */
  public VariablesDisplay(Workspace workspace) {
    super(workspace);
    variablesBox = setupVBoxContainer(VARIABLES_TITLE, VARIABLES_BOX_ID);
  }

  /**
   * Updates the scrolling VBox of the variables display view with tags that correspond to
   * the most updated list of all currently existing variables.
   * @param variablesMap Map that holds the names of all currently existing variables as
   *                     keys and their corresponding values as values.
   */
  public void updateBox(Map<String, Double> variablesMap) {
    variablesBox.getChildren().clear();

    for (Map.Entry<String, Double> entry : variablesMap.entrySet()) {
      addNewVariablesTag(entry.getKey(), entry.getValue());
    }
  }

  private void addNewVariablesTag(String name, double value) {
    Button variablesTag = new Button(String.format("%s = %.2f", name.substring(1), value));
    variablesTag.setWrapText(true);
    variablesTag.setMaxWidth(Double.MAX_VALUE);
    variablesTag.setMaxHeight(Double.MAX_VALUE);
    variablesTag.setId(ID_BUNDLE.getString(VARIABLES_TAG_ID));

    applyVariablesTagLogic(variablesTag);
    variablesBox.getChildren().add(variablesTag);
  }

  private void applyVariablesTagLogic(Button variablesTag) {
    variablesTag.setOnAction(e -> {
      String[] variableMap = variablesTag.getText().split("= ");
      String variableName = variableMap[0];
      String variableValue = variableMap[1];
      TextInputDialog textDialog = new TextInputDialog(String.format("%s", variableValue));
      textDialog.setHeaderText(String.format("%s %s", DIALOG_BOX_HEADER_TEXT, variableName));
      textDialog.showAndWait();

      String newValue = textDialog.getEditor().getText();
      if (newValue != null && !newValue.equals(variableValue)) {
        String command = String.format("%s%s%s", SET_COMMAND, variableName, newValue);
        setTerminalText(command);
      }
    });
  }
}
