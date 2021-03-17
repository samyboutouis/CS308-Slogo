package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

// represents the actual node when a user calls the command they defined, so return value is not just 1 or 0
public class CommandNode extends SlogoNode {

  private List<SlogoNode> myCommands;
  private List<SlogoNode> parameters;
  private List<VariableNode> variables;

  public CommandNode(int numParameters, List<SlogoNode> myCommands, List<VariableNode> variables) {
    super(numParameters);
    parameters = super.getParameters();
    this.myCommands = myCommands;
    this.variables = variables;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    double ret = 0;
    setParameters(commands);
    for(SlogoNode node : myCommands) {
      ret = node.getReturnValue(commands);
    }
    return ret;
  }

  // size of parameters must be equal to size of variables
  private void setParameters(List<Command> commands) {
    for(int i = 0; i < variables.size(); i++){
      variables.get(i).setValue(parameters.get(i).getReturnValue(commands));
    }
  }
}
