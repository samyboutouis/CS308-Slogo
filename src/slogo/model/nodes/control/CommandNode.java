package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

// represents the actual node when a user calls the command they defined, so return value is not just 1 or 0
public class CommandNode extends SlogoNode {

  private List<SlogoNode> myCommands;
  private List<SlogoNode> parameters;
  private List<VariableNode> variables;
  private List<Double> prevValues;

  public CommandNode(int numParameters, List<SlogoNode> myCommands, List<VariableNode> variables) {
    super(numParameters);
    parameters = super.getParameters();
    this.myCommands = myCommands;
    this.variables = variables;
    prevValues = new ArrayList<>();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    double ret = 0;
    setParameters(tracker);
    for (SlogoNode node : myCommands) {
      ret = node.getReturnValue(tracker);
    }
    updateVariableScope();
    return ret;
  }

  // size of parameters must be equal to size of variables
  private void setParameters(BackEndTurtleTracker tracker) {
    for (int i = 0; i < variables.size(); i++) {
      // keep track of previous values once this command returns
      prevValues.add(variables.get(i).getReturnValue(tracker));
      variables.get(i).setValue(parameters.get(i).getReturnValue(tracker));
    }
  }

  private void updateVariableScope() {
    for (int i = 0; i < variables.size(); i++) {
      // if variable was undefined before, it's just 0 since it wasn't in the map
      variables.get(i).setValue(prevValues.get(i));
    }
  }
}
