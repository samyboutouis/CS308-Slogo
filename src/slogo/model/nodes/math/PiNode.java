package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class PiNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public PiNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.PI;
  }
}