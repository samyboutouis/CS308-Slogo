package slogo.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

public class VariablesDisplay extends ScrollingDisplay {
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private final static String VARIABLES_TITLE = "VariablesTitle";
  private final static String VARIABLES_BOX_ID = "VariablesBoxID";
  private final static String VARIABLES_TAG_ID = "VariablesTagID";

  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final VBox variablesBox;

  /**
   *
   * @param resourcePackage
   */
  public VariablesDisplay(String resourcePackage){
    super(resourcePackage);

    variablesBox = setupVBoxContainer(VARIABLES_TITLE, VARIABLES_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
  }

  /**
   *
   * @param variablesMap
   */
  public void updateBox(Map<String, Double> variablesMap){
    variablesBox.getChildren().clear();

    for(Map.Entry<String, Double> entry : variablesMap.entrySet()){
      addNewVariablesTag(entry.getKey(), entry.getValue());
    }
  }

  private void addNewVariablesTag(String name, double value){
    Button variablesTag = new Button(String.format("%s : %.2f", name.substring(1), value));
    variablesTag.setMaxWidth(Double.MAX_VALUE);
    variablesTag.setMaxHeight(Double.MAX_VALUE);
    variablesTag.setId(idBundle.getString(VARIABLES_TAG_ID));
    applyVariablesTagLogic(variablesTag);

    variablesBox.getChildren().add(variablesTag);
  }

  private void applyVariablesTagLogic(Button variablesTag) {
    variablesTag.setOnAction(e -> {
      String[] variableMap = variablesTag.getText().split(":");
      String variableName = variableMap[0];
      String variableValue = variableMap[1];
      TextInputDialog textDialog = new TextInputDialog(String.format("%s", variableValue));
      textDialog.setHeaderText(String.format("Set new value for %s", variableName));
      textDialog.showAndWait();

      // for testing
      if(textDialog.getEditor().getText() != null){
        System.out.println(textDialog.getEditor().getText());
      }

      // for real:
      // call method in backend to change variable value
      // call updateBox()
    });
  }
}
