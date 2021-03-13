package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.turtlecommands.HomeCommand;

public class HomeNode extends SlogoNode {

  public HomeNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new HomeCommand());
    return 0;
  }
}
