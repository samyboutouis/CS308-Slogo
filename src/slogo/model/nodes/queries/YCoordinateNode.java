package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class YCoordinateNode extends TurtleCommandNode {
  public YCoordinateNode(int parameter){
    super(parameter);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("Y Coordinate: "+turtle.getY()));
    return turtle.getY();
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values)->{
      String mesg = String.format("Turtle %d Y Coordinate is %.2f", currTurtle.getIndex(), currTurtle.getY());
      System.out.println(mesg);
      currTurtle.addCommand(new DisplayCommand(mesg));
      return currTurtle.getY();
    });
  }
}
