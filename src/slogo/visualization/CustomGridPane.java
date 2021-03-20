package slogo.visualization;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CustomGridPane extends GridPane {

  private final int rowCount;
  private final int colCount;
  private final int paddingLength;
  private final GridPane pane;
  private final static String DISPLAY_CLASS_NAME = "displayWindow";

  public CustomGridPane(int rowCount, int colCount, int paddingLength){
    this.rowCount = rowCount;
    this.colCount = colCount;
    this.paddingLength = paddingLength;

    pane = new GridPane();

    initializeGridSize();
    initializeGridRowsAndCols();
  }

  /**
   *
   * @param scene
   */
  public void setPrefSize(Scene scene){
    pane.setPrefSize(scene.getWidth(), scene.getHeight());
  }

  private void initializeGridSize() {
    pane.setMinSize(0, 0);
    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    pane.setVgap(paddingLength);
    pane.setHgap(paddingLength);
    pane.setPadding(new Insets(paddingLength));
  }

  private void initializeGridRowsAndCols() {
    for (int i = 0; i < rowCount; i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / rowCount);
      pane.getRowConstraints().add(row);
    }

    for (int i = 0; i < colCount; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(100.0 / colCount);
      pane.getColumnConstraints().add(col);
    }
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
  }
}
