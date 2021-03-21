package slogo.model.nodes.control;

import java.util.Map;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class VariableNode extends SlogoNode {

  private Map<String, Double> variables;
  private String name;

  public VariableNode(int numParameters, Map<String, Double> variables, String name) {
    super(numParameters); // dummy value for parameters
    this.variables = variables;
    this.name = name;
  }

  public void setValue(double value) {
    variables.put(name, value);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (!variables.containsKey(name)) {
      return 0;
    } else {
      return variables.get(name);
    }
  }
}
