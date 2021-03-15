package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class RandomNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public RandomNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return Math.random()* parameters.get(0).getReturnValue(commands);
  }
}