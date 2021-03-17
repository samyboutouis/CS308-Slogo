package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.PenUpCommand;

public class PenUpNode extends SlogoNode {
  private Turtle turtle;

  public PenUpNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenUpCommand());
    turtle.penUp();
    return 0;
  }
}
