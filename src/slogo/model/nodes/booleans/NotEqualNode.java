package slogo.model.nodes.booleans;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class NotEqualNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public NotEqualNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    if(Double.compare(parameters.get(0).getReturnValue(commands), parameters.get(1).getReturnValue(commands)) == 0){
      return 0;
    }
    return 1;
  }
}
