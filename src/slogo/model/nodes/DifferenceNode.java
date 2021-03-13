package slogo.model.nodes;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class DifferenceNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public DifferenceNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return parameters.get(0).getReturnValue(commands) - parameters.get(1).getReturnValue(commands);
  }
}