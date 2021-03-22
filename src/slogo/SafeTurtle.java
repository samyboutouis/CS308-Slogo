package slogo;

import java.io.File;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

public interface SafeTurtle {

  double getX();

  double getY();

  double getDirection();

  boolean isPenDown();

  void penDown();

  void penUp();

  boolean isShowing();
}
