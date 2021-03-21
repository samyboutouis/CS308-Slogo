package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

public class HistoryDisplay extends ScrollingDisplay {
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private final static String HISTORY_TITLE = "HistoryTitle";
  private final static String HISTORY_BOX_ID = "HistoryBoxID";
  private final static String HISTORY_TAG_ID = "HistoryTagID";

  private final ResourceBundle idBundle;
  private final ResourceBundle resourceBundle;

  private final VBox historyBox;

  public HistoryDisplay(Workspace workspace, String resourcePackage){
    super(workspace, resourcePackage);
    historyBox = setupVBoxContainer(HISTORY_TITLE, HISTORY_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
  }

  /**
   *
   * @param command
   */
  public Button addNewHistoryTag(String command){
    Button historyTag = new Button(command);
    historyTag.setWrapText(true);
    historyTag.setMaxWidth(Double.MAX_VALUE);
    historyTag.setMaxHeight(Double.MAX_VALUE);
    historyTag.setId(idBundle.getString(HISTORY_TAG_ID));

    historyBox.getChildren().add(historyTag);

    applyHistoryTagLogic(historyTag);

    return historyTag;
  }

  public void applyHistoryTagLogic(Button historyTag){
    historyTag.setOnAction(e -> {
      String command = historyTag.getText();
      getTerminalDisplay().setTerminalText(command);
    });
  }
}
