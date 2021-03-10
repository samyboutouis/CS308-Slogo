package slogo;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Pen {
  private final GridPane gridPane;
  private boolean isPenDown;
  private Color lineColor;

  public Pen(GridPane gridPane) {
    this.gridPane = gridPane;
    isPenDown = true;
    lineColor = Color.BLACK;
  }

  public void drawLine(double startX, double startY, double endX, double endY) {
    if(isPenDown) {
      Line line = new Line(startX, startY, endX, endY);
      line.setTranslateX(startX);
      line.setTranslateY(startY);
      line.setStroke(lineColor);
      gridPane.add(line, 0, 0);
    }
  }

  public void setColor(Color color) {
    lineColor = color;
  }

  public void penDown() {
    isPenDown = true;
  }

  public void penUp() {
    isPenDown = false;
  }
}
