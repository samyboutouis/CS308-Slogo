package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.turtlecommands.ShowTurtleCommand;

public class ShowTurtleNode extends SlogoNode {

  public ShowTurtleNode(int numParameters) {
    super(numParameters);
  }
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new ShowTurtleCommand());
    return 1;
  }
}
