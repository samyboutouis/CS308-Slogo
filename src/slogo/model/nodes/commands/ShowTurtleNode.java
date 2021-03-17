package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.ShowTurtleCommand;

public class ShowTurtleNode extends SlogoNode {

  private Turtle turtle;

  public ShowTurtleNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new ShowTurtleCommand());
    turtle.show();
    return 1;
  }
}
