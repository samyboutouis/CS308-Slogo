package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class IDNode extends SlogoNode {

  public IDNode(int numParameters){
    super(numParameters);
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return 0;
  }
}
