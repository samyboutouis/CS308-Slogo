package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class IDNode extends SlogoNode {

  public IDNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return tracker.getCurr();
  }
}
