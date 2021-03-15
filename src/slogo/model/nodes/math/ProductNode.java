package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class ProductNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public ProductNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return parameters.get(0).getReturnValue(commands) * parameters.get(1).getReturnValue(commands);
  }
}
