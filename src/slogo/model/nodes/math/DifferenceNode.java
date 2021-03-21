package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class DifferenceNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public DifferenceNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return parameters.get(0).getReturnValue(tracker) - parameters.get(1).getReturnValue(tracker);
  }
}