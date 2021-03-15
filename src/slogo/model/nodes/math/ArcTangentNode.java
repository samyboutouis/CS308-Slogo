package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class ArcTangentNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public ArcTangentNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return Math.atan(parameters.get(0).getReturnValue(commands));
  }
}
