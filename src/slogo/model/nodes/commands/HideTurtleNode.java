package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.HideTurtleCommand;

public class HideTurtleNode extends SlogoNode {
  private Turtle turtle;

  public HideTurtleNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new HideTurtleCommand());
    turtle.hide();
    return 1;
  }
}