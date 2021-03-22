package slogo;

// allow easy transfer from frontend turtle to backend turtle; restricts what methods
// backend turtle can call to create itself
public interface SafeTurtle {

  double getX();

  double getY();

  double getDirection();

  boolean isPenDown();

  boolean isShowing();

  int getPenColorIndex();

  int getShapeIndex();
}
