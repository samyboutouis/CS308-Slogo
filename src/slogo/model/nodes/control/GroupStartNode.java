package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class GroupStartNode extends SlogoNode {

  public GroupStartNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return 0;
  }
}