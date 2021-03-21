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

  public CustomGridPane(int rowCount, int colCount, int paddingLength) {
    this.rowCount = rowCount;
    this.colCount = colCount;
    this.paddingLength = paddingLength;

    initializeGridSize();
    initializeGridRowsAndCols();
  }

  /**
   * @param scene
   */
  public void setPrefSize(Scene scene) {
    this.setPrefSize(scene.getWidth(), scene.getHeight());
  }

  private void initializeGridSize() {
    this.setMinSize(0, 0);
    this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.setVgap(paddingLength);
    this.setHgap(paddingLength);
    this.setPadding(new Insets(paddingLength));
  }

  private void initializeGridRowsAndCols() {
    for (int i = 0; i < rowCount; i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / rowCount);
      this.getRowConstraints().add(row);
    }

    for (int i = 0; i < colCount; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(100.0 / colCount);
      this.getColumnConstraints().add(col);
    }
  }
}
