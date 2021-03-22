package slogo.visualization.displays;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import slogo.visualization.Workspace;

public class ScrollingDisplay {

  private static final ResourceBundle resourceBundle = ResourceBundle
      .getBundle("resources/languages/English");
  private static final ResourceBundle idBundle = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final int PADDING_LENGTH = 10;
  private static final int VBOX_PADDING_LENGTH = 5;
  private static final int ROW_COUNT = 10;
  private static final String DISPLAY_CLASS_NAME = "displayWindow";

  private final GridPane pane;
  private final Workspace workspace;

  public ScrollingDisplay(Workspace workspace) {
    this.workspace = workspace;
    pane = new GridPane();
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
  }

  /**
   * @param title
   * @param vBoxID
   * @return
   */
  public VBox setupVBoxContainer(String title, String vBoxID) {
    pane.setVgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH));

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    col.setPercentWidth(100.0);
    pane.getColumnConstraints().add(col);

    for (int i = 0; i < ROW_COUNT; i++) {
      RowConstraints row = new RowConstraints();
      row.setVgrow(Priority.ALWAYS);
      row.setPercentHeight(100.0 / ROW_COUNT);
      pane.getRowConstraints().add(row);
    }

    initializeTitleLabel(title);
    return initializeVBox(vBoxID);
  }

  private void initializeTitleLabel(String title) {
    Label titleLabel = new Label(resourceBundle.getString(title));
    pane.add(titleLabel, 0, 0, 1, 1);
  }

  private VBox initializeVBox(String vBoxID) {
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(1);

    scrollPane.setId(idBundle.getString(vBoxID));
    pane.add(scrollPane, 0, 1, 1, 9);

    VBox vBox = new VBox();
    vBox.setFillWidth(true);
    vBox.setSpacing(VBOX_PADDING_LENGTH);
    vBox.setPadding(new Insets(VBOX_PADDING_LENGTH));

    scrollPane.setContent(vBox);

    return vBox;
  }

  public GridPane getPane() {
    return pane;
  }

  public TerminalDisplay getTerminalDisplay() {
    return workspace.getTerminalDisplay();
  }
}
