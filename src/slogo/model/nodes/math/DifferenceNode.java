package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class DifferenceNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public DifferenceNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return parameters.get(0).getReturnValue(tracker) - parameters.get(1).getReturnValue(tracker);
  }
}