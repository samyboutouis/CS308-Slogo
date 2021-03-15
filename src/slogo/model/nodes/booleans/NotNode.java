package slogo.model.nodes.booleans;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class NotNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public NotNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    if(parameters.get(0).getReturnValue(commands) == 0.0){
      return 1;
    }
    return 0;
  }
}
