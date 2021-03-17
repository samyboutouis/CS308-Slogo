package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.PenDownCommand;

public class PenDownNode extends SlogoNode {
  private Turtle turtle;

  public PenDownNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenDownCommand());
    turtle.penDown();
    return 1;
  }
}
