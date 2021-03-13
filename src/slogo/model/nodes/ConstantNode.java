package slogo.model.nodes;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class ConstantNode extends SlogoNode {
  private double value;

  public ConstantNode(int numParameters, double value) {
    super(numParameters); // should be 0 when created
    this.value = value;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return value;
  }

}
