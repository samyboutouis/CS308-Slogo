package slogo.visualization.displays;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

public class VariablesDisplay extends ScrollingDisplay {

  private static final ResourceBundle idBundle = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final String VARIABLES_TITLE = "VariablesTitle";
  private static final String VARIABLES_BOX_ID = "VariablesBoxID";
  private static final String VARIABLES_TAG_ID = "VariablesTagID";
  private static final String DIALOG_BOX_HEADER_TEXT = "Set a new value for:";
  private static final String SET_COMMAND = "set :";

  private final VBox variablesBox;

  public VariablesDisplay(Workspace workspace) {
    super(workspace);
    variablesBox = setupVBoxContainer(VARIABLES_TITLE, VARIABLES_BOX_ID);
  }

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
    variablesTag.setId(idBundle.getString(VARIABLES_TAG_ID));
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
        getTerminalDisplay().setTerminalText(command);
      }
    });
  }
}
