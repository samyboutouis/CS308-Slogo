package slogo.visualization.displays;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

public class HistoryDisplay extends ScrollingDisplay {

  private static final ResourceBundle idBundle = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final String HISTORY_TITLE = "HistoryTitle";
  private static final String HISTORY_BOX_ID = "HistoryBoxID";
  private static final String HISTORY_TAG_ID = "HistoryTagID";


  private final VBox historyBox;

  public HistoryDisplay(Workspace workspace) {
    super(workspace);
    historyBox = setupVBoxContainer(HISTORY_TITLE, HISTORY_BOX_ID);
  }

  /**
   * @param command
   */
  public void addNewHistoryTag(String command) {
    Button historyTag = new Button(command);
    historyTag.setWrapText(true);
    historyTag.setMaxWidth(Double.MAX_VALUE);
    historyTag.setMaxHeight(Double.MAX_VALUE);
    historyTag.setId(idBundle.getString(HISTORY_TAG_ID));

    historyBox.getChildren().add(historyTag);

    applyHistoryTagLogic(historyTag);
  }

  public void applyHistoryTagLogic(Button historyTag) {
    historyTag.setOnAction(e -> {
      String command = historyTag.getText();
      getTerminalDisplay().setTerminalText(command);
    });
  }
}
