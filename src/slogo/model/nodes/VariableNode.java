package slogo.model.nodes;

import java.util.List;
import java.util.Map;
import slogo.Command;
import slogo.model.SlogoNode;

public class VariableNode extends SlogoNode {
  private Map<String, Double> variables;
  private String name;

  public VariableNode(int numParameters, Map<String, Double> variables, String name) {
    super(numParameters); // dummy value for parameters
    this.variables = variables;
    this.name = name;
  }

  public void setValue(double value){
    variables.put(name, value);
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    if(!variables.containsKey(name)){
      return 0;
    }
    else {
      return variables.get(name);
    }
  }
}
