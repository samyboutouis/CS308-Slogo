package slogo.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Pen {

  private static final String LINE_ID = "LineID";
  private static final double DEFAULT_WIDTH = 1.0;

  private final ResourceBundle idBundle;
  private final Pane pane;
  private final List<Line> lineList;
  private boolean isPenDown;
  private Color lineColor;
  private double strokeWidth;

  public Pen(Pane pane, ResourceBundle resourceBundle) {
    this.pane = pane;
    isPenDown = true;
    lineColor = Color.BLACK;
    lineList = new ArrayList<>();
    idBundle = resourceBundle;
    strokeWidth = DEFAULT_WIDTH;
  }

  public void drawLine(double startX, double startY, double changeX, double changeY) {
    if (isPenDown) {
      Line line = new Line(0, 0, changeX, changeY);
      line.setStroke(lineColor);
      line.setStrokeWidth(strokeWidth);
      line.setTranslateX(startX);
      line.setTranslateY(startY);
      line.setId(idBundle.getString(LINE_ID));
      pane.getChildren().add(line);
      lineList.add(line);
    }
  }

  public void setColor(Color color) {
    lineColor = color;
  }

  public void setThickness(double width) {
    strokeWidth = width;
  }

  public void penDown() {
    isPenDown = true;
  }

  public void penUp() {
    isPenDown = false;
  }

  public void removeLines() {
    for (Line line : lineList) {
      pane.getChildren().remove(line);
    }
  }

  public boolean isPenDown() {
    return isPenDown;
  }

  public Color getColor() {
    return lineColor;
  }

  public double getThickness() {
    return strokeWidth;
  }
}
