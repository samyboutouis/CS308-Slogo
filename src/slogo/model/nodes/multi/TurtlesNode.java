package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class TurtlesNode extends SlogoNode {

  public TurtlesNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return tracker.turtles();
  }
}
