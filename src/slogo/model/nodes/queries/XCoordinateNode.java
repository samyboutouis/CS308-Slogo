package slogo.model.nodes.queries;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.DisplayCommand;

public class XCoordinateNode extends SlogoNode {
  private Turtle turtle;
  public XCoordinateNode(int parameter, BackEndTurtle turtle){
    super(parameter);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("X Coordinate: "+turtle.getX()));
    System.out.println(turtle.getX());
    return turtle.getX();
  }
}
