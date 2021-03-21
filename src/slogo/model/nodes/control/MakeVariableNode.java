package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

// implements make and set commands
public class MakeVariableNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public MakeVariableNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    double value = parameters.get(1).getReturnValue(tracker);
    SlogoNode leftChild = parameters.get(0);
    if (leftChild instanceof VariableNode) {
      ((VariableNode) leftChild).setValue(value);
    } else {
      // some error
      System.out.println("make/set was not given a variable");
    }
    return value;
  }
}
