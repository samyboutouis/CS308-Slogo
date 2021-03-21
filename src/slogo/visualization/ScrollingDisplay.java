package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ScrollingDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static int VBOX_PADDING_LENGTH = 5;
  private final static int ROW_COUNT = 10;
  private final static String DISPLAY_CLASS_NAME = "displayWindow";

  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final GridPane pane;

  /**
   *
   * @param resourcePackage
   */
  public ScrollingDisplay(String resourcePackage){
    pane = new GridPane();
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "stylesheets", "CSS_IDs"));
  }

  /**
   *
   * @param title
   * @param vBoxID
   * @return
   */
  public VBox setupVBoxContainer(String title, String vBoxID){
    pane.setVgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH));

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

    initializeTitleLabel(title);
    return initializeVBox(vBoxID);
  }

  private void initializeTitleLabel(String title){
    Label titleLabel = new Label(resourceBundle.getString(title));
    pane.add(titleLabel, 0, 0, 1, 1);
  }

  private VBox initializeVBox(String vBoxID){
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

  public GridPane getPane(){
    return pane;
  }
}
