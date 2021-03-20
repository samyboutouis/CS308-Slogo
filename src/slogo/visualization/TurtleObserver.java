package slogo.visualization;

import java.util.List;
import slogo.SafeTurtle;

public interface TurtleObserver {
  void updateTurtleNumber(List<Integer> list);
  void updateTurtleState(int id);
}
