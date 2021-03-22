package slogo.visualization.observers;

import java.util.List;

public interface TurtleObserver {

  void updateTurtleNumber(List<Integer> list);

  void updateTurtleState(int id);
}
