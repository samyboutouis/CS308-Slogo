package slogo;

public interface Turtle {
  void forward(double pixels);

  void back(double pixels);

  void rotate(double directionChange);

  void setDirection(double direction);

  void setXY(double xPosition, double yPosition);

  void towards(double xPosition, double yPosition);

  double getX();

  double getY();

  double getDirection();

  void home();

  void clearScreen();
}
