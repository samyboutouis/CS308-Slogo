package slogo.visualization.observers;

import javafx.scene.paint.Color;
import slogo.visualization.turtle.FrontEndTurtle;

public interface BackgroundObserver {

  void setBackgroundColor(Color color);

  void addToBackground(FrontEndTurtle turtle);

  Color getBackgroundColor();
}
