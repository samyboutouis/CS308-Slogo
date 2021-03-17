package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class ListEndNode extends SlogoNode {

  public ListEndNode(int numParameters) {
    super(numParameters); // numParameters = 0, so it is always full
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    // nodes that use bracket node will know to ignore the bracket return value
    return 0;
  }
}