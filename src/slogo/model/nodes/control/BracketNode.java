package slogo.model.nodes.control;

import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;

public class BracketNode extends SlogoNode {


  public BracketNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public boolean isFull() {

  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return 0;
  }
}
