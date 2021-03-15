package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.turtlecommands.HideTurtleCommand;

public class HideTurtleNode extends SlogoNode {

  public HideTurtleNode(int numParameters) {
    super(numParameters);
  }
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new HideTurtleCommand());
    return 1;
  }
}