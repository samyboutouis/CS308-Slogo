package slogo.model;

import java.util.List;
import java.util.Map;
import slogo.Command;
import slogo.model.nodes.VariableNode;

// implements make and set commands
public class AssignNode extends SlogoNode{
  private List<SlogoNode> parameters;
  private Map<String, Double> variables;

  public AssignNode(String type, Map<String, Double> variables){
    super(2, type);
    parameters = super.getParameters();
    this.variables = variables;
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
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
