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

  /**
   * Creates a line based on the coordinates given by the turtle and adds it to the screen through
   * a pane.
   *
   * @param startX double representing the x-coordinate of one of the points of the line
   * @param startY double representing the y-coordinate of one of the points of the line
   * @param changeX double representing the x-coordinate of the other point of the line
   * @param changeY double representing the y-coordinate of the other point of the line
   */
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

  /**
   * Sets the color of the pen when it draws new lines on the screen.
   *
   * @param color Color object representing the desired color of the new line
   */
  public void setColor(Color color) {
    lineColor = color;
  }

  /**
   * Sets the thickness of the lines drawn by the pen.
   *
   * @param width double representing the thickness of the lines drawn by the pen
   */
  public void setThickness(double width) {
    strokeWidth = width;
  }

  /**
   * Sets the pen status to down so that when the turtle moves, lines are drawn.
   */
  public void penDown() {
    isPenDown = true;
  }

  /**
   * Sets the pen status to up so that when the turtle moves, no lines are drawn.
   */
  public void penUp() {
    isPenDown = false;
  }

  /**
   * Removes all the lines drawn on the pane. Typically called when clearing the screen from the cs
   * command.
   */
  public void removeLines() {
    for (Line line : lineList) {
      pane.getChildren().remove(line);
    }
  }

  /**
   * Gets the status of the pen whether it is up or down
   *
   * @return boolean representing status of pen (true if pen is down, false if pen is up)
   */
  public boolean isPenDown() {
    return isPenDown;
  }

  /**
   * Gets the current color the pen is drawing on the screen.
   *
   * @return Color representing the pen color
   */
  public Color getColor() {
    return lineColor;
  }

  /**
   * Gets the thickness of the lines drawn by the pen.
   *
   * @return double representing the thickness of the lines drawn by the pen
   */
  public double getThickness() {
    return strokeWidth;
  }

  /**
   * Information regarding all the lines drawn on the screen by a certain turtle for file
   * saving purposes.
   *
   * @return a list containing key-value pairs for each line indicating its start and end pos,
   * translate properties, line color, and line thickness.
   */
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
