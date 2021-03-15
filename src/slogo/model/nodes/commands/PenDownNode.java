package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.turtlecommands.PenDownCommand;

public class PenDownNode extends SlogoNode {

  public PenDownNode(int numParameters) {
    super(numParameters);
  }
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenDownCommand());
    return 1;
  }
}
