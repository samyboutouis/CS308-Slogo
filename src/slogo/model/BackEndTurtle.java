package slogo.model;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.SafeTurtle;

/**
 * Represents the turtle moving around in the backend. Does not get displayed and is not affected
 * by any user actions. For each call of parseInput, the front end will convert all front end
 * turtles to backend turtles with the exact same properties.
 *
 * Used for commands that want to know the current state of the turtle, so we must move our
 * backend turtle along as we do commands, and we can't move the front end turtle because that
 * would prevent the front end from having control of when they get to display the turtle
 * movements.
 *
 *      backEndAllTurtles.put(id, new BackEndTurtle(allTurtles.get(id), id));
 *
 *  From front end turtle tracker. Create a back end turtle just by passing in the front end turtle
 *  and the ID.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class BackEndTurtle {

  private double xCoordinate;
  private final int ID;
  private double yCoordinate;
  private double direction;
  private boolean isPenDown;
  private boolean isShowing;
  private int penColorIndex;
  private int shapeIndex;
  private List<Command> commands;

  /**
   * Constructor used by front end to quickly create back end turtles just by passing in the
   * interface used to transfer from front end to backend turtle.
   *
   * @param transfer interface type that has getters for turtle state that backend needs.
   * @param ID ID of this turtle.
   */
  public BackEndTurtle(SafeTurtle transfer, int ID) {
    this.ID = ID; // talk about where ID should be stored
    this.xCoordinate = transfer.getX();
    this.yCoordinate = transfer.getY();
    this.direction = transfer.getDirection();
    this.isPenDown = transfer.isPenDown();
    this.isShowing = transfer.isShowing();
    this.shapeIndex = transfer.getShapeIndex();
    this.penColorIndex = transfer.getPenColorIndex();
    commands = new ArrayList<>();
  }

  /**
   * Constructor used for testing, to create a new turtle with the given values.
   *
   * @param xCoordinate start x-cor of turtle
   * @param yCoordinate start y-cor of turtle
   * @param direction initial direction of turtle
   * @param isPenDown boolean for pen down
   * @param isShowing boolean for turtle shown
   * @param ID id of turtle
   */
  public BackEndTurtle(double xCoordinate, double yCoordinate, double direction, boolean isPenDown,
      boolean isShowing, int ID) {
    this.ID = ID;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.direction = direction;
    this.isPenDown = isPenDown;
    this.isShowing = isShowing;
    commands = new ArrayList<>();
  }

  /**
   * Returns the list of commands for this turtle.
   *
   * @return list of commands of this turtle, to be ran on the corresponding front end turtle.
   */
  public List<Command> getCommands() {
    return commands;
  }

  /**
   * Returns ID of this turtle.
   *
   * @return ID of this turtle.
   */
  public int getIndex() {
    return this.ID;
  }

  /**
   * Removes all commands in this turtle.
   *
   * Used to delete all data in the tracker, mainly to prevent issues between tests.
   */
  public void clearCommands() {
    commands.clear();
  }

  /**
   * Add a command object to the end of the commands list.
   *
   * Called in node classes that will affect the turtle state on the front end.
   *
   * @param n new command object to add to this list of commands.
   */
  public void addCommand(Command n) {
    commands.add(n);
  }

  /**
   * Moves the turtle forward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void forward(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    xCoordinate += xChange;
    yCoordinate += yChange;
  }

  /**
   * Moves the turtle backward by a certain amount.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void back(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    xCoordinate -= xChange;
    yCoordinate -= yChange;
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
  }

  /**
   * Rotates the turtle to a certain heading.
   *
   * @param direction angle representing the heading of a turtle
   */
  public void setDirection(double direction) {
    this.direction = direction;
  }

  /**
   * Moves the turtle to a certain x and y-coordinate.
   *
   * @param xPosition double representing the x-coordinate of the turtle
   * @param yPosition double representing the y-coordinate of the turtle
   */
  public void setXY(double xPosition, double yPosition) {
    xCoordinate = xPosition;
    yCoordinate = yPosition;
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
    return isPenDown;
  }

  /**
   * Sets the pen status to down.
   */
  public void penDown() {
    isPenDown = true;
  }

  /**
   * Sets the pen status to up.
   */
  public void penUp() {
    isPenDown = false;
  }

  /**
   * Gets the status of the turtle whether it is showing or not.
   *
   * @return boolean representing status of showing (true if turtle is showing, false if hiding)
   */
  public boolean isShowing() {
    return isShowing;
  }

  /**
   * Makes turtle visible on the screen.
   */
  public void show() {
    isShowing = true;
  }

  /**
   * Makes turtle invisible on the screen.
   */
  public void hide() {
    isShowing = false;
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
    setDirection(0);
  }

  /**
   * Sets the turtle's pen color index.
   *
   * @param index Integer representing the index of the certain pen color in the palette.
   */
  public void setPenColorIndex(int index) {
    penColorIndex = index;
  }

  /**
   * Sets the turtle's image to a shape in the palette based on the index provided.
   *
   * On the backend this just keeps track of the current index that is set on the turtle, in case
   * user wants to query that index before it is passed to front end.
   *
   * @param index Integer representing the index of the certain shape in the palette.
   */
  public void setShapeIndex(int index) {
    shapeIndex = index;
  }

  /**
   * Gets the current color index the pen is drawing on the screen.
   *
   * @return integer representing the pen color index
   */
  public int getPenColorIndex() {
    return penColorIndex;
  }

  /**
   * Get the current index of the shape that the turtle currently has.
   *
   * @return Integer representing the index of the shape.
   */
  public int getShapeIndex() {
    return shapeIndex;
  }
}