package slogo;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Pen {
  private final AnchorPane anchorPane;
  private boolean isPenDown;
  private Color lineColor;

  public Pen(AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
    isPenDown = true;
    lineColor = Color.BLACK;
  }

  public void drawLine(double startX, double startY, double changeX, double changeY) {
    if(isPenDown) {
      Line line = new Line(0, 0, changeX, changeY);
      line.setStroke(lineColor);
      line.setTranslateX(startX);
      line.setTranslateY(startY);
      anchorPane.getChildren().add(line);
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
