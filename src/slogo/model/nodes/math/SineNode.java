package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class SineNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public SineNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return Math.sin(Math.toRadians(parameters.get(0).getReturnValue(tracker)));
  }
}
