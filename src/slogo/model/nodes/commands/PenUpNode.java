package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.turtlecommands.PenUpCommand;

public class PenUpNode extends SlogoNode {

  public PenUpNode(int numParameters) {
    super(numParameters);
  }
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenUpCommand());
    return 0;
  }
}
