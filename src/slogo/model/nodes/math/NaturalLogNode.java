package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class NaturalLogNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public NaturalLogNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.log(parameters.get(0).getReturnValue(tracker));
  }
}

