package slogo.visualization.turtle;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slogo.SafeTurtle;

/**
 * Class that represents the turtle displayed on the front-end. In charge of determining the image
 * the turtle takes on and moving it on the screen when certain methods are called on it such as
 * forward, backward, rotate, etc. In charge of drawing all the lines on the screen from the pen
 * that this turtle holds. Is dependent on the FrontEndTurtleTracker as it adds itself to the list
 * of turtles on the screen.
 *
 * @author Samy Boutouis
 */
public class FrontEndTurtle implements Turtle, SafeTurtle {

  private static final String DEFAULT_IMAGE = "resources/turtle.png";
  private static final int IMAGE_HEIGHT = 50;
  private static final int IMAGE_WIDTH = 50;
  private static final int ACTIVE_IMAGE_HEIGHT = 70;
  private static final int ACTIVE_IMAGE_WIDTH = 70;

  private ImageView imageView;
  private double xCoordinate;
  private double yCoordinate;
  private double direction;
  private Pen pen;
  private final ResourceBundle idBundle;
  private final FrontEndTurtleTracker turtleTracker;
  private ActiveCircle activeCircle;
  private boolean isActive;
  private int shapeIndex;
  private int penColorIndex;

  /**
   * Constructor for class.
   *
   * @param frontEndTurtleTracker TurtleTracker object that is in charge of tracking all front-end
   *                              turtles created.
   */
  public FrontEndTurtle(FrontEndTurtleTracker frontEndTurtleTracker) {
    this.idBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "stylesheets", "CSS_IDs"));
    setDefaultImage();
    isActive = true;
    turtleTracker = frontEndTurtleTracker;
    shapeIndex = 1;
    penColorIndex = 3;
  }

  /**
   * Moves the turtle forward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void forward(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    double penX = imageView.getX() + imageView.getTranslateX() + IMAGE_WIDTH / 2.0;
    double penY = imageView.getY() + imageView.getTranslateY() + IMAGE_HEIGHT / 2.0;
    pen.drawLine(penX, penY, xChange, -yChange);
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate += xChange;
    yCoordinate += yChange;
    activeCircle.updatePosition(xChange, -yChange);
  }

  /**
   * Moves the turtle backward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void back(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    double penX = imageView.getX() + imageView.getTranslateX() + IMAGE_WIDTH / 2.0;
    double penY = imageView.getY() + imageView.getTranslateY() + IMAGE_HEIGHT / 2.0;
    pen.drawLine(penX, penY, -xChange, yChange);
    imageView.setTranslateX(imageView.getTranslateX() - xChange);
    imageView.setTranslateY(imageView.getTranslateY() + yChange);
    xCoordinate -= xChange;
    yCoordinate -= yChange;
    activeCircle.updatePosition(-xChange, yChange);
  }

  private double calculateComponentX(double pixels) {
    return Math.sin(Math.toRadians(direction)) * pixels;
  }

  private double calculateComponentY(double pixels) {
    return Math.cos(Math.toRadians(direction)) * pixels;
  }

  /**
   * Rotates the turtle by a certain amount.
   *
   * @param directionChange amount representing direction the turtle changes in degrees
   */
  public void rotate(double directionChange) {
    direction += directionChange;
    imageView.setRotate(imageView.getRotate() + directionChange);
  }

  /**
   * Rotates the turtle to a certain heading.
   *
   * @param direction angle representing the heading of a turtle
   */
  public void setDirection(double direction) {
    this.direction = direction;
    imageView.setRotate(direction);
  }

  /**
   * Moves the turtle to a certain x and y-coordinate.
   *
   * @param xPosition double representing the x-coordinate of the turtle
   * @param yPosition double representing the y-coordinate of the turtle
   */
  public void setXY(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate = xPosition;
    yCoordinate = yPosition;
    activeCircle.setXY(xPosition, yPosition);
  }

  /**
   * Rotates the turtle so it is facing a certain coordinate.
   *
   * @param xPosition double representing the x-coordinate of the turtle
   * @param yPosition double representing the y-coordinate of the turtle
   */
  public void towards(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    double direction = Math.toDegrees(Math.atan2(xChange, yChange));
    setDirection(direction);
  }

  /**
   * Makes turtle visible on the screen.
   */
  public void show() {
    imageView.setVisible(true);
    activeCircle.show();
  }

  /**
   * Makes turtle invisible on the screen.
   */
  public void hide() {
    imageView.setVisible(false);
    activeCircle.hide();
  }

  /**
   * Gets the status of the turtle whether it is showing or not.
   *
   * @return boolean representing status of showing (true if turtle is showing, false if hiding)
   */
  public boolean isShowing() {
    return imageView.isVisible();
  }

  /**
   * Gets the current color the pen is drawing on the screen.
   *
   * @return Color representing the pen color
   */
  public Color getPenColor() {
    return pen.getColor();
  }

  /**
   * Sets the image used to represent the turtle on the screen.
   *
   * @param file File object containing the image file that the user wants to use
   */
  public void setImage(File file) {
    Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, true, false);
    imageView.setImage(image);
  }

  /**
   * Sets the image used to represent the turtle on the screen using a file path.
   *
   * @param filePath String that represents a file path that leads to an image file
   */
  public void setImage(String filePath) {
    Image image = new Image(filePath, IMAGE_WIDTH, IMAGE_HEIGHT, true, false);
    imageView.setImage(image);
  }

  /**
   * Sets the turtle's image to a shape in the palette based on the index provided.
   *
   * @param index Integer representing the index of the certain shape in the palette.
   */
  public void setShape(int index) {
    shapeIndex = index;
    String filePath = turtleTracker.getShapeFromIndex(index);
    setImage(filePath);
  }

  /**
   * Get the current index of the shape that the turtle currently has.
   *
   * @return Integer representing the index of the shape.
   */
  public int getShapeIndex() {
    return shapeIndex;
  }

  private void setDefaultImage() {
    Image image = new Image(DEFAULT_IMAGE, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    imageView = new ImageView(image);
    imageView.setId(idBundle.getString("Turtle"));
    activeCircle = new ActiveCircle(ACTIVE_IMAGE_WIDTH, ACTIVE_IMAGE_HEIGHT);
    imageView.setOnMouseClicked(event -> toggleActive());
  }

  /**
   * Given a pane, adds the turtle to the screen in the center, adds its associated active circle,
   * and creates the pen.
   *
   * @param turtlePane Pane that the turtle will appear on.
   * @param height Value representing the height of the pane.
   * @param width Value representing the width of the pane.
   */
  public void addToScreen(Pane turtlePane, double height, double width) {
    imageView.setTranslateX(width / 2 - IMAGE_WIDTH / 2);
    imageView.setTranslateY(height / 2 - IMAGE_HEIGHT / 2);
    ImageView activeCircleView = activeCircle.getImageView();
    activeCircleView.setTranslateX(width / 2 - ACTIVE_IMAGE_WIDTH / 2);
    activeCircleView.setTranslateY(height / 2 - ACTIVE_IMAGE_HEIGHT / 2);
    turtlePane.getChildren().add(imageView);
    turtlePane.getChildren().add(activeCircleView);
    pen = new Pen(turtlePane, idBundle);
  }

  /**
   * Changes the color of the pen the turtle is drawing on the screen.
   *
   * @param color Color representing the pen color
   */
  public void setPenColor(Color color) {
    pen.setColor(color);
  }

  /**
   * Changes the color of the pen the turtle is drawing on the screen based on the index in the
   * palette.
   *
   * @param index Integer representing the index of the color in the palette.
   */
  public void setPenColor(int index) {
    penColorIndex = index;
    Color color = turtleTracker.getColorFromIndex(index);
    setPenColor(color);
  }

  /**
   * Get the current index of the color that the turtle currently has.
   *
   * @return Index of the color the pen is based on the palette.
   */
  public int getPenColorIndex() {
    return penColorIndex;
  }

  /**
   * Gets the thickness of the lines drawn by the pen.
   *
   * @return double representing the thickness of the lines drawn by the pen
   */
  public double getPenThickness() {
    return pen.getThickness();
  }

  /**
   * Sets the thickness of the lines drawn by the pen.
   *
   * @param width double representing the thickness of the lines drawn by the pen
   */
  public void setPenThickness(double width) {
    pen.setThickness(width);
  }

  /**
   * Sets the pen status to down.
   */
  public void penDown() {
    pen.penDown();
  }

  /**
   * Sets the pen status to up.
   */
  public void penUp() {
    pen.penUp();
  }

  /**
   * Gets the current x-coordinate of the turtle.
   *
   * @return double representing the x-position of the turtle
   */
  public double getX() {
    return xCoordinate;
  }

  /**
   * Gets the current y-coordinate of the turtle.
   *
   * @return double representing the y-position of the turtle
   */
  public double getY() {
    return yCoordinate;
  }

  /**
   * Gets the heading of the turtle.
   *
   * @return double representing the heading of the turtle
   */
  public double getDirection() {
    return direction;
  }

  /**
   * Gets the status of the pen whether it is up or down
   *
   * @return boolean representing status of pen (true if pen is down, false if pen is up)
   */
  public boolean isPenDown() {
    return pen.isPenDown();
  }

  /**
   * Moves the turtle to the 0, 0 x-y coordinate and sets the heading to 0.
   */
  public void home() {
    setXY(0, 0);
  }

  /**
   * Removes all lines on the screen drawn by the turtle and resets the turtle to home.
   */
  public void clearScreen() {
    home();
    pen.removeLines();
    setDirection(0);
  }

  private void toggleActive() {
    if (isActive) {
      setInactive();
    } else {
      setActive();
    }
  }

  /**
   * Gets the status of whether a turtle is active or not on the screen.
   *
   * @return boolean representing whether turtle is active (true if turtle is active, false if inactive)
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Sets the turtle's state to active and update the corresponding active circle.
   */
  public void setActive() {
    activeCircle.show();
    isActive = true;
    turtleTracker.setActive(this);
  }

  /**
   * Sets the turtle's state to inactive and update the corresponding active circle.
   */
  public void setInactive() {
    activeCircle.hide();
    isActive = false;
    turtleTracker.setInactive(this);
  }

  /**
   * Information regarding all the lines drawn on the screen by a certain turtle for file
   * saving purposes.
   *
   * @return a list containing key-value pairs for each line indicating its start and end pos,
   * translate properties, line color, and line thickness.
   */
  public List<Map<String, String>> getLineInfo() {
    return pen.getLineInfo();
  }
}