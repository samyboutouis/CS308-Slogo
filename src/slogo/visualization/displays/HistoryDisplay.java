package slogo.visualization.displays;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

/**
 * The HistoryDisplay class is responsible for creating an instance of the history display view,
 * including all of its UI components, and managing the creation/modification of tags from certain
 * actions (e.g., creating a tag with the last written command whenever a command is run on the terminal).
 *
 * @author Donghan Park
 */
public class HistoryDisplay extends ScrollingDisplay {

  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final String HISTORY_TITLE = "HistoryTitle";
  private static final String HISTORY_BOX_ID = "HistoryBoxID";
  private static final String HISTORY_TAG_ID = "HistoryTagID";

  private final VBox historyBox;

  /**
   * Constructor that creates an instance of the HistoryDisplay object.
   * @param workspace Reference to the workspace object, which encapsulates all display views
   */
  public HistoryDisplay(Workspace workspace) {
    super(workspace);
    historyBox = setupVBoxContainer(HISTORY_TITLE, HISTORY_BOX_ID);
  }

  /**
   * Adds a new history tag to the display view; a history tag is essentially a button with the
   * last written command as its text value.
   * @param command The last written command on the terminal to display on the history tag
   */
  public void addNewHistoryTag(String command) {
    Button historyTag = new Button(command);
    historyTag.setWrapText(true);
    historyTag.setMaxWidth(Double.MAX_VALUE);
    historyTag.setMaxHeight(Double.MAX_VALUE);
    historyTag.setId(ID_BUNDLE.getString(HISTORY_TAG_ID));

    historyBox.getChildren().add(historyTag);
    applyHistoryTagLogic(historyTag);
  }

  /**
   * Applies the logic of changing the terminal's text whenever a history tag is clicked.
   * @param historyTag The reference to the button representing the history tag
   */
  public void applyHistoryTagLogic(Button historyTag) {
    historyTag.setOnAction(e -> {
      String command = historyTag.getText();
      setTerminalText(command);
    });
  }
}
