package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class ConstantNode extends SlogoNode {
  private double value;

  public ConstantNode(int numParameters, double value) {
    super(numParameters); // should be 0 when created
    this.value = value;
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return value;
  }

}
