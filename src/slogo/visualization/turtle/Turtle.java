package slogo.visualization.turtle;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

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

  boolean isPenDown();

  void penDown();

  void penUp();

  boolean isShowing();

  void show();

  void hide();

  void home();

  void clearScreen();

  Color getPenColor();

  void setPenColor(Color color);

  double getPenThickness();

  void setPenThickness(double width);

  void setImage(File file);

  List<Map<String, String>> getLineInfo();

  boolean isActive();
}
