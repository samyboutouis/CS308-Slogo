package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class GroupEndNode extends SlogoNode {

  public GroupEndNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return 0;
  }
}