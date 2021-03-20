package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class ListEndNode extends SlogoNode {

  public ListEndNode(int numParameters) {
    super(numParameters); // numParameters = 0, so it is always full
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // nodes that use bracket node will know to ignore the bracket return value
    return 0;
  }
}