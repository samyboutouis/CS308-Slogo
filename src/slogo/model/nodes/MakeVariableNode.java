package slogo.model.nodes;

import java.util.List;
import java.util.Map;
import slogo.Command;
import slogo.model.SlogoNode;

// implements make and set commands
public class MakeVariableNode extends SlogoNode {
  private List<SlogoNode> parameters;
  private Map<String, Double> variables;

  public MakeVariableNode(int numParameters, Map<String, Double> variables){
    super(numParameters);
    parameters = super.getParameters();
    this.variables = variables;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    double value = parameters.get(1).getReturnValue(commands);
    SlogoNode leftChild = parameters.get(0);
    if(leftChild instanceof VariableNode){
      ((VariableNode) leftChild).setValue(value);
    }
    else{
      // some error
      System.out.println("make/set was not given a variable");
    }
    return value;
  }
}
