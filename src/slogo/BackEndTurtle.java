package slogo;

public class BackEndTurtle implements Turtle {

  private double xCoordinate;
  private double yCoordinate;
  private double direction;
  private boolean isPenDown;
  private boolean isShowing;

  public BackEndTurtle(double xCoordinate, double yCoordinate, double direction, boolean isPenDown, boolean isShowing) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.direction = direction;
    this.isPenDown = isPenDown;
    this.isShowing = isShowing;
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