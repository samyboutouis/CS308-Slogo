package slogo.model.nodes.queries;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.DisplayCommand;

public class HeadingNode extends SlogoNode {
  private Turtle turtle;
  public HeadingNode(int parameter, BackEndTurtle turtle){
    super(parameter);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("Heading: "+turtle.getDirection()));
    return turtle.getDirection();
  }
}