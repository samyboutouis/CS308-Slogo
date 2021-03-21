package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class ConstantNode extends SlogoNode {

  private double value;

  public ConstantNode(int numParameters, double value) {
    super(numParameters); // should be 0 when created
    this.value = value;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return value;
  }

}
