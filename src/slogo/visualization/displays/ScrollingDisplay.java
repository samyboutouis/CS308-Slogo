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

/**
 * The ScrollingDisplay class is responsible for creating and setting up the essential
 * UI components needed for all scrolling display views, including the variables, palette,
 * user-defined commands, and history views.
 *
 * @author Donghan Park
 */
public class ScrollingDisplay {

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle("resources/languages/English");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final int PADDING_LENGTH = 10;
  private static final int VBOX_PADDING_LENGTH = 5;
  private static final int ROW_COUNT = 10;
  private static final String DISPLAY_CLASS_NAME = "displayWindow";
  private static final String SCROLLING_ID = "ScrollingBox";

  private final GridPane pane;
  private final Workspace workspace;

  /**
   * Constructor that creates an instance of the ScrollingDisplay object.
   * @param workspace Reference to the workspace object, which encapsulates all display views.
   */
  public ScrollingDisplay(Workspace workspace) {
    this.workspace = workspace;
    pane = new GridPane();
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
  }

  /**
   * Sets up and initializes the root grid pane for the display view that acts as a
   * vertical box container, which holds all of the UI components found within each
   * view. Returns the reference to the created scrolling VBox.
   * @param title Title of the display view
   * @param vBoxID CSS ID of the display view
   * @return Reference to the created scrolling VBox that will hold all of the tags
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
    Label titleLabel = new Label(RESOURCE_BUNDLE.getString(title));
    pane.add(titleLabel, 0, 0, 1, 1);
  }

  private VBox initializeVBox(String vBoxID) {
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(1);
    scrollPane.setId(SCROLLING_ID);
    pane.add(scrollPane, 0, 1, 1, 9);

    VBox vBox = new VBox();
    vBox.setId(ID_BUNDLE.getString(vBoxID));
    vBox.setFillWidth(true);
    vBox.setSpacing(VBOX_PADDING_LENGTH);
    vBox.setPadding(new Insets(VBOX_PADDING_LENGTH));
    scrollPane.setContent(vBox);

    return vBox;
  }

  /**
   * Returns the root pane of the display view.
   * @return Root pane of the display view
   */
  public GridPane getPane() {
    return pane;
  }

  /**
   * Sets the text of the terminal text field with a desired command.
   * @param command Desired command to set the text of the terminal text field with
   */
  public void setTerminalText(String command) {
    workspace.getTerminalDisplay().setTerminalText(command);
  }
}
