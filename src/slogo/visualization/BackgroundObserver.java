package slogo.visualization;

import javafx.scene.paint.Color;

public interface BackgroundObserver {

  void setBackgroundColor(Color color);

  void addToBackground(FrontEndTurtle turtle);

  Color getBackgroundColor();
}
