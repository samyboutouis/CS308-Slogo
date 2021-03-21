package slogo.visualization;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

public class UserCommandsDisplay extends ScrollingDisplay {

  private final static String USER_COMMANDS_TITLE = "UserCommandsTitle";
  private final static String USER_COMMANDS_BOX_ID = "UserCommandsBoxID";
  private final static String USER_COMMANDS_TAG_ID = "UserCommandsTagID";

  private final static String DIALOG_BOX_HEADER_TEXT = "Set the parameters, with each parameter separated by spaces, for the command:";

  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final VBox userCommandsBox;

  public UserCommandsDisplay(Workspace workspace, String resourcePackage){
    super(workspace, resourcePackage);

    userCommandsBox = setupVBoxContainer(USER_COMMANDS_TITLE, USER_COMMANDS_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "stylesheets", "CSS_IDs"));
  }

  /**
   *
   * @param commandsMap
   */
  public void updateBox(Map<String, String> commandsMap){
    userCommandsBox.getChildren().clear();

    for(Map.Entry<String, String> entry : commandsMap.entrySet()){
      addNewCommandsTag(entry.getKey(), entry.getValue());
    }
  }

  private void addNewCommandsTag(String name, String value){
    Button commandsTag = new Button(String.format("%s ::%s", name, value));
    commandsTag.setWrapText(true);
    commandsTag.setMaxWidth(Double.MAX_VALUE);
    commandsTag.setMaxHeight(Double.MAX_VALUE);
    commandsTag.setId(idBundle.getString(USER_COMMANDS_TAG_ID));

    userCommandsBox.getChildren().add(commandsTag);

    applyCommandsTagLogic(commandsTag);
  }

  private void applyCommandsTagLogic(Button commandsTag) {
    commandsTag.setOnAction(e -> {
      String[] commandsMap = commandsTag.getText().split(":: ");
      String commandName = commandsMap[0];

      TextInputDialog textDialog = new TextInputDialog();
      textDialog.setHeaderText(String.format("%s %s", DIALOG_BOX_HEADER_TEXT, commandName));
      textDialog.showAndWait();

      String newValue = textDialog.getEditor().getText();
      if(newValue != null){
        String command = String.format("%s%s", commandName, newValue);
        getTerminalDisplay().setTerminalText(command);
      }
    });
  }
}
