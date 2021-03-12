package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserCommandsDisplay extends ScrollingDisplay {

  private final static String USER_COMMANDS_TITLE = "UserCommandsTitle";
  private final static String USER_COMMANDS_BOX_ID = "UserCommandsBoxID";
  private final static String USER_COMMANDS_TAG_ID = "UserCommandsTagID";

  private final ResourceBundle resourceBundle;
  private final VBox userCommandsBox;

  public UserCommandsDisplay(GridPane pane, String resourcePackage){
    super(pane, resourcePackage);

    userCommandsBox = setupVBoxContainer(USER_COMMANDS_TITLE, USER_COMMANDS_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));

    updateBox(new String[]{"yeet", "ok", "example"}); //test
  }

  /**
   *
   * @param commandsList
   */
  public void updateBox(String[] commandsList){
    userCommandsBox.getChildren().clear();

    for (String commandName : commandsList) {
      addNewVariablesTab(commandName);
    }
  }

  private void addNewVariablesTab(String variableName){
    Button commandsTab = new Button(variableName);
    commandsTab.setMaxWidth(Double.MAX_VALUE);
    commandsTab.setMaxHeight(Double.MAX_VALUE);
    commandsTab.setId(resourceBundle.getString(USER_COMMANDS_TAG_ID));

    userCommandsBox.getChildren().add(commandsTab);
  }
}
