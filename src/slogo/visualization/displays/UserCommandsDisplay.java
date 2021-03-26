package slogo.visualization.displays;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

/**
 * The UserCommandsDisplay class is responsible for creating an instance of the user-defined
 * commands display view, including all of its UI components, and managing the creation/modification
 * of tags from certain actions (e.g., when a new user-defined command is created from the terminal).
 *
 * @author Donghan Park
 */
public class UserCommandsDisplay extends ScrollingDisplay {

  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final String USER_COMMANDS_TITLE = "UserCommandsTitle";
  private static final String USER_COMMANDS_BOX_ID = "UserCommandsBoxID";
  private static final String USER_COMMANDS_TAG_ID = "UserCommandsTagID";
  private static final String DIALOG_BOX_HEADER_TEXT = "Set the parameters, with each parameter separated by spaces, for the command:";

  private final VBox userCommandsBox;

  /**
   * Constructor that creates an instance of the UserCommandsDisplay.
   * @param workspace Reference to the workspace object, which encapsulates all display views.
   */
  public UserCommandsDisplay(Workspace workspace) {
    super(workspace);
    userCommandsBox = setupVBoxContainer(USER_COMMANDS_TITLE, USER_COMMANDS_BOX_ID);
  }

  /**
   * Updates the scrolling VBox of the user-commands display view with tags that correspond
   * to the most updated list of all currently existing user-defined commands.
   * @param commandsMap Map that holds the names of all currently existing user-defined commands
   *                    as keys and their corresponding commands as values
   */
  public void updateBox(Map<String, String> commandsMap) {
    userCommandsBox.getChildren().clear();
    for (Map.Entry<String, String> entry : commandsMap.entrySet()) {
      addNewCommandsTag(entry.getKey(), entry.getValue());
    }
  }

  private void addNewCommandsTag(String name, String value) {
    Button commandsTag = new Button(String.format("%s =%s", name, value));
    commandsTag.setWrapText(true);
    commandsTag.setMaxWidth(Double.MAX_VALUE);
    commandsTag.setMaxHeight(Double.MAX_VALUE);
    commandsTag.setId(ID_BUNDLE.getString(USER_COMMANDS_TAG_ID));

    userCommandsBox.getChildren().add(commandsTag);
    applyCommandsTagLogic(commandsTag);
  }

  private void applyCommandsTagLogic(Button commandsTag) {
    commandsTag.setOnAction(e -> {
      String[] commandsMap = commandsTag.getText().split("= ");
      String commandName = commandsMap[0];

      TextInputDialog textDialog = new TextInputDialog();
      textDialog.setHeaderText(String.format("%s %s", DIALOG_BOX_HEADER_TEXT, commandName));
      textDialog.showAndWait();

      String newValue = textDialog.getEditor().getText();
      if (newValue != null) {
        String command = String.format("%s%s", commandName, newValue);
        setTerminalText(command);
      }
    });
  }
}
