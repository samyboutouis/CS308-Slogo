package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

// represents the actual node when a user calls the command they defined, so return value is not just 1 or 0
public class UserDefinedNode extends SlogoNode {

  private List<SlogoNode> myCommands;

  public UserDefinedNode(int numParameters, List<SlogoNode> myCommands) {
    super(numParameters);
    this.myCommands = myCommands;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    double ret = 0;
    for(SlogoNode node : myCommands) {
      ret = node.getReturnValue(commands);
    }
    return ret;
  }
}
