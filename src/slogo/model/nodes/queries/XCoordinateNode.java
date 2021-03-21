package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class XCoordinateNode extends TurtleCommandNode {

  public XCoordinateNode(int parameter) {
    super(parameter);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("X Coordinate: "+turtle.getX()));
    return turtle.getX();
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      String mesg = String
          .format("Turtle %d X Coordinate is %.2f", currTurtle.getIndex(), currTurtle.getX());
      System.out.println(mesg);
      currTurtle.addCommand(new DisplayCommand(mesg));
      return currTurtle.getX();
    });
  }
}
