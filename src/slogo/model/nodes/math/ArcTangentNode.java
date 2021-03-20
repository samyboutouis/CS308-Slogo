package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class ArcTangentNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public ArcTangentNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.atan(Math.toRadians( parameters.get(0).getReturnValue(tracker)));
  }
}
