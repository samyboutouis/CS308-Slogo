package slogo.visualization.turtle;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

/**
 * Interface representing a generic turtle and actions that need to be performed on a turtle.
 * Classes that implement this interface will handle all the methods that a potential turtle must
 * do such as moving forward, backward, hiding, etc. In general, this interface would be implemented
 * in a front-end environment because it is dependent on JavaFX objects and classes such as Color.
 * This interface is used to encapsulate the implementation of different types of turtles and only
 * call methods that need to be called in other classes.
 *
 * @author Samy Boutouis
 */
public interface Turtle {

  /**
   * Moves the turtle forward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  void forward(double pixels);

  /**
   * Moves the turtle backward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  void back(double pixels);

  /**
   * Rotates the turtle by a certain amount.
   *
   * @param directionChange amount representing direction the turtle changes in degrees
   */
  void rotate(double directionChange);

  /**
   * Rotates the turtle to a certain heading.
   *
   * @param direction angle representing the heading of a turtle
   */
  void setDirection(double direction);

  /**
   * Moves the turtle to a certain x and y-coordinate.
   *
   * @param xPosition double representing the x-coordinate of the turtle
   * @param yPosition double representing the y-coordinate of the turtle
   */
  void setXY(double xPosition, double yPosition);

  /**
   * Rotates the turtle so it is facing a certain coordinate.
   *
   * @param xPosition double representing the x-coordinate of the turtle
   * @param yPosition double representing the y-coordinate of the turtle
   */
  void towards(double xPosition, double yPosition);

  /**
   * Gets the current x-coordinate of the turtle.
   *
   * @return double representing the x-position of the turtle
   */
  double getX();

  /**
   * Gets the current y-coordinate of the turtle.
   *
   * @return double representing the y-position of the turtle
   */
  double getY();

  /**
   * Gets the heading of the turtle.
   *
   * @return double representing the heading of the turtle
   */
  double getDirection();

  /**
   * Gets the status of the pen whether it is up or down
   *
   * @return boolean representing status of pen (true if pen is down, false if pen is up)
   */
  boolean isPenDown();

  /**
   * Sets the pen status to down.
   */
  void penDown();

  /**
   * Sets the pen status to up.
   */
  void penUp();

  /**
   * Gets the status of the turtle whether it is showing or not.
   *
   * @return boolean representing status of showing (true if turtle is showing, false if hiding)
   */
  boolean isShowing();

  /**
   * Makes turtle visible on the screen.
   */
  void show();

  /**
   * Makes turtle invisible on the screen.
   */
  void hide();

  /**
   * Moves the turtle to the 0, 0 x-y coordinate and sets the heading to 0.
   */
  void home();

  /**
   * Removes all lines on the screen drawn by the turtle and resets the turtle to home.
   */
  void clearScreen();

  /**
   * Gets the current color the pen is drawing on the screen.
   *
   * @return Color representing the pen color
   */
  Color getPenColor();

  /**
   * Changes the color of the pen the turtle is drawing on the screen.
   *
   * @param color Color representing the pen color
   */
  void setPenColor(Color color);

  /**
   * Gets the thickness of the lines drawn by the pen.
   *
   * @return double representing the thickness of the lines drawn by the pen
   */
  double getPenThickness();

  /**
   * Sets the thickness of the lines drawn by the pen.
   *
   * @param width double representing the thickness of the lines drawn by the pen
   */
  void setPenThickness(double width);

  /**
   * Sets the image used to represent the turtle on the screen.
   *
   * @param file File object containing the image file that the user wants to use
   */
  void setImage(File file);

  /**
   * Information regarding all the lines drawn on the screen by a certain turtle for file
   * saving purposes.
   *
   * @return a list containing key-value pairs for each line indicating its start and end pos,
   * translate properties, line color, and line thickness.
   */
  List<Map<String, String>> getLineInfo();

  /**
   * Gets the status of whether a turtle is active or not on the screen.
   *
   * @return boolean representing whether turtle is active (true if turtle is active, false if inactive)
   */
  boolean isActive();
}
