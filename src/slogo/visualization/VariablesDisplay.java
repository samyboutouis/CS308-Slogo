package slogo.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
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
      addNewVariablesTab(entry.getKey(), entry.getValue());
    }
  }

  private void addNewVariablesTab(String name, double value){
    Button variablesTab = new Button(String.format("%s : %.2f", name.substring(1), value));
    variablesTab.setMaxWidth(Double.MAX_VALUE);
    variablesTab.setMaxHeight(Double.MAX_VALUE);
    variablesTab.setId(idBundle.getString(VARIABLES_TAG_ID));

    variablesBox.getChildren().add(variablesTab);
  }
}
