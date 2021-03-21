package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class TangentNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public TangentNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.tan(Math.toRadians(parameters.get(0).getReturnValue(tracker)));
  }
}

