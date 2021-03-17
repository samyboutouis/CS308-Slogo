package slogo.model.nodes.queries;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class XCoordinateNode extends TurtleCommandNode {
  private Turtle turtle;
  public XCoordinateNode(int parameter, BackEndTurtle turtle){
    super(parameter);
    this.turtle = turtle;
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("X Coordinate: "+turtle.getX()));
    return turtle.getX();
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values)->{
      String mesg = String.format("Turtle %d X Coordinate is %.2f", currTurtle.getIndex(), currTurtle.getX());
      currTurtle.addCommand(new DisplayCommand(mesg));
      return currTurtle.getX();
    });
  }
}
