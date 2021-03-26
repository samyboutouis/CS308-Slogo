package slogo.visualization.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Responsible for drawing all the lines when a turtle moves. In most instances, this class would
 * belong to a turtle object and draw the lines associated with a turtle. It is dependent on a pane
 * that the pen draws lines on and a resource bundle to set the IDs of lines.
 *
 * @author Samy Boutouis
 */
public class Pen {

  private static final String LINE_ID = "LineID";
  private static final double DEFAULT_WIDTH = 1.0;

  private final ResourceBundle idBundle;
  private final Pane pane;
  private final List<Line> lineList;
  private boolean isPenDown;
  private Color lineColor;
  private double strokeWidth;

  /**
   * Constructor for pen.
   *
   * @param pane Pane that the pen will draw its lines on to show on the screen.
   * @param resourceBundle
   */
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

  public List<Map<String, String>> getLineInfo() {
    List<Map<String, String>> listInfo = new ArrayList<>();
    for (Line line : lineList) {
      Map<String, String> map = new HashMap<>();
      map.put("TranslateX", String.valueOf(line.getTranslateX()));
      map.put("TranslateY", String.valueOf(line.getTranslateY()));
      map.put("EndX", String.valueOf(line.getEndX()));
      map.put("EndY", String.valueOf(line.getEndY()));
      map.put("Color", line.getStroke().toString());
      map.put("Width", String.valueOf(line.getStrokeWidth()));
      listInfo.add(map);
    }
    return listInfo;
  }
}
