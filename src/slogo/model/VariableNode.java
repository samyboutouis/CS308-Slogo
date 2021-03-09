package slogo.model;

import java.util.List;
import java.util.Map;
import slogo.Command;

public class VariableNode extends SlogoNode{
  private Map<String, Double> variables;
  private String name;

  public VariableNode(String type, Map<String, Double> variables, String name) {
    super(0, type); // dummy value for parameters
    this.name = name;
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    if(!variables.containsKey(name)){
      return 0;
    }
    else {
      return variables.get(name);
    }
  }
}
