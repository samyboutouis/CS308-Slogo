package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class HistoryDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static int ROW_COUNT = 10;
  private final static String HISTORY_TITLE = "HistoryTitle";
  private final static String HISTORY_BOX_ID = "HistoryBoxID";
  private final static String HISTORY_TAB_ID = "HistoryTabID";

  private final GridPane pane;
  private ScrollPane scrollPane;
  private VBox historyBox;

  private final ResourceBundle resourceBundle;

  public HistoryDisplay(GridPane pane, String resourcePackage){
    this.pane = pane;

    pane.setMaxWidth(Double.MAX_VALUE);
    pane.setMaxHeight(Double.MAX_VALUE);

    pane.setVgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH));

    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    col.setPercentWidth(100.0);
    pane.getColumnConstraints().add(col);

    for(int i = 0; i < ROW_COUNT; i++){
      RowConstraints row = new RowConstraints();
      row.setVgrow(Priority.ALWAYS);
      row.setPercentHeight(100.0 / ROW_COUNT);
      pane.getRowConstraints().add(row);
    }

    initializeTitleLabel();
    initializeHistoryBox();
  }

  private void initializeTitleLabel(){
    Label title = new Label(resourceBundle.getString(HISTORY_TITLE));
    pane.add(title, 0, 0, 1, 1);
  }

  private void initializeHistoryBox(){
    scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(1);

    scrollPane.setId(resourceBundle.getString(HISTORY_BOX_ID));
    pane.add(scrollPane, 0, 1, 1, 9);

    historyBox = new VBox();
    historyBox.setFillWidth(true);
    historyBox.setMaxHeight(Double.MAX_VALUE);
    historyBox.setSpacing(PADDING_LENGTH);
    historyBox.setPadding(new Insets(PADDING_LENGTH));

    scrollPane.setContent(historyBox);
  }

  /**
   *
   * @param command
   */
  public Button addNewHistoryTab(String command){
    Button historyTab = new Button(command);
    historyTab.setMaxWidth(Double.MAX_VALUE);
    historyTab.setMaxHeight(Double.MAX_VALUE);
    historyTab.setId(resourceBundle.getString(HISTORY_TAB_ID));

    historyBox.getChildren().add(historyTab);

    scrollPane.setVvalue(1.0); // doesnt auto scroll all the way down... wtf

    return historyTab;
  }
}
