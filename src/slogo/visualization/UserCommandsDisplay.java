package slogo.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class UserCommandsDisplay extends ScrollingDisplay {

  private final static String USER_COMMANDS_TITLE = "UserCommandsTitle";
  private final static String USER_COMMANDS_BOX_ID = "UserCommandsBoxID";
  private final static String USER_COMMANDS_TAG_ID = "UserCommandsTagID";

  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final VBox userCommandsBox;

  public UserCommandsDisplay(String resourcePackage){
    super(resourcePackage);

    userCommandsBox = setupVBoxContainer(USER_COMMANDS_TITLE, USER_COMMANDS_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "stylesheets", "CSS_IDs"));
  }

  /**
   *
   * @param commandsMap
   */
  public void updateBox( Map<String, String> commandsMap){
    userCommandsBox.getChildren().clear();

    for(Map.Entry<String, String> entry : commandsMap.entrySet()){
      addNewCommandsTag(entry.getKey(), entry.getValue());
    }
  }

  private void addNewCommandsTag(String name, String value){
    Button commandsTag = new Button(String.format("%s : %s", name, value));
    commandsTag.setWrapText(true);
    commandsTag.setMaxWidth(Double.MAX_VALUE);
    commandsTag.setMaxHeight(Double.MAX_VALUE);
    commandsTag.setId(idBundle.getString(USER_COMMANDS_TAG_ID));

    userCommandsBox.getChildren().add(commandsTag);
  }
}
