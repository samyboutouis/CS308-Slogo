package slogo;

import javafx.scene.paint.Color;

public interface SafeTurtle {
  
  double getX();

  double getY();

  double getDirection();

  boolean isPenDown();

  void penDown();

  void penUp();
  
  boolean isShowing();

  Color getPenColor();

  void setPenColor(Color color);

  double getPenThickness();

  void setPenThickness(double width);
}
