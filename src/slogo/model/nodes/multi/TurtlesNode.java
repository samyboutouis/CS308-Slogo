package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class TurtlesNode extends SlogoNode {

  public TurtlesNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return tracker.turtles();
  }
}
