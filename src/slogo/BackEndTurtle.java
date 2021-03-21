package slogo;

import java.util.ArrayList;
import java.util.List;

public class BackEndTurtle implements Turtle {

  private double xCoordinate;
  private final int ID;
  private double yCoordinate;
  private double direction;
  private boolean isPenDown;
  private boolean isShowing;
  private List<Command> commands;

  public BackEndTurtle(SafeTurtle transfer) {
    this.ID = 0; // talk about where ID should be stored
    this.xCoordinate = transfer.getX();
    this.yCoordinate = transfer.getY();
    this.direction = transfer.getDirection();
    this.isPenDown = transfer.isPenDown();
    this.isShowing = transfer.isShowing();
    commands = new ArrayList<>();
  }

  public BackEndTurtle(double xCoordinate, double yCoordinate, double direction, boolean isPenDown, boolean isShowing, int ID) {
    this.ID = ID;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.direction = direction;
    this.isPenDown = isPenDown;
    this.isShowing = isShowing;
    commands = new ArrayList<>();
  }

  public List<Command> getCommands(){
    return commands;
  }

  public int getIndex(){return this.ID;}

  public void clearCommands(){
    commands.clear();
  }

  public void addCommand(Command n) {
    commands.add(n);
  }

  public void forward(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    xCoordinate += xChange;
    yCoordinate += yChange;
  }

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

  public void rotate(double directionChange) {
    direction += directionChange;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  public void setXY(double xPosition, double yPosition) {
    xCoordinate = xPosition;
    yCoordinate = yPosition;
  }

  public void towards(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    double direction = Math.toDegrees(Math.atan2(xChange, yChange));
    setDirection(direction);
  }

  public double getX() {
    return xCoordinate;
  }

  public double getY() {
    return yCoordinate;
  }

  public double getDirection() { return direction; }

  public boolean isPenDown() { return isPenDown; }

  public void penDown() { isPenDown = true; }

  public void penUp() { isPenDown = false; }

  public boolean isShowing() {
    return isShowing;
  }

  public void show(){
    isShowing = true;
  }

  public void hide(){
    isShowing = false;
  }

  public void home() {
    setXY(0, 0);
  }

  public void clearScreen() {
    home();
    setDirection(0);
  }
}