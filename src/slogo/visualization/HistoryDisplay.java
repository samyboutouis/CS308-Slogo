package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class HistoryDisplay extends ScrollingDisplay {

  private final static String HISTORY_TITLE = "HistoryTitle";
  private final static String HISTORY_BOX_ID = "HistoryBoxID";
  private final static String HISTORY_TAG_ID = "HistoryTagID";

  private final ResourceBundle resourceBundle;

  private final VBox historyBox;

  public HistoryDisplay(GridPane pane, String resourcePackage){
    super(pane, resourcePackage);

    historyBox = setupVBoxContainer(HISTORY_TITLE, HISTORY_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
  }

  /**
   *
   * @param command
   */
  public Button addNewHistoryTab(String command){
    Button historyTab = new Button(command);
    historyTab.setMaxWidth(Double.MAX_VALUE);
    historyTab.setMaxHeight(Double.MAX_VALUE);
    historyTab.setId(resourceBundle.getString(HISTORY_TAG_ID));

    historyBox.getChildren().add(historyTab);

    return historyTab;
  }
}
