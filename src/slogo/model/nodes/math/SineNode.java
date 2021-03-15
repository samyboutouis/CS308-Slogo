package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class SineNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public SineNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return Math.sin(parameters.get(0).getReturnValue(commands));
  }
}
