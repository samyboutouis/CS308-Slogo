package slogo;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Pen {
  private final Pane pane;
  private boolean isPenDown;
  private Color lineColor;
  private List<Line> lineList;

  public Pen(Pane pane) {
    this.pane = pane;
    isPenDown = true;
    lineColor = Color.BLACK;
    lineList = new ArrayList<>();
  }

  public void drawLine(double startX, double startY, double changeX, double changeY) {
    if(isPenDown) {
      Line line = new Line(0, 0, changeX, changeY);
      line.setStroke(lineColor);
      line.setTranslateX(startX);
      line.setTranslateY(startY);
      pane.getChildren().add(line);
      lineList.add(line);
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

  public void removeLines() {
    for(Line line : lineList) {
      pane.getChildren().remove(line);
    }
  }
}
