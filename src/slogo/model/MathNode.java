package slogo.model;

import java.util.List;
import slogo.Command;

public class MathNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public MathNode(int numParameters, String type){
    super(numParameters, type);
    parameters = super.getParameters();
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    switch(super.getType()){
      case "Sum" -> {
        return parameters.get(0).getReturnValue(commands) +
            parameters.get(1).getReturnValue(commands);
      }
      case "Difference" -> {
        return parameters.get(0).getReturnValue(commands) -
            parameters.get(1).getReturnValue(commands);
      }
      default -> throw new IllegalStateException("Unexpected value: " + super.getType());
    }
  }
}
