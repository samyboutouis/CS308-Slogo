package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class CosineNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public CosineNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return Math.cos(Math.toRadians( parameters.get(0).getReturnValue(tracker)));
  }
}
