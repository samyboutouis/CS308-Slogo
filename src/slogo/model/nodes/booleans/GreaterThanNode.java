package slogo.model.nodes.booleans;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class GreaterThanNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public GreaterThanNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    if(parameters.get(0).getReturnValue(commands) > parameters.get(1).getReturnValue(commands)){
      return 1;
    }
    return 0;
  }
}