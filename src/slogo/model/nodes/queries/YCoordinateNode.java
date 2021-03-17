package slogo.model.nodes.queries;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.DisplayCommand;

public class YCoordinateNode extends SlogoNode {
  private Turtle turtle;
  public YCoordinateNode(int parameter, BackEndTurtle turtle){
    super(parameter);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("Y Coordinate: "+turtle.getY()));
    return turtle.getY();
  }
}
