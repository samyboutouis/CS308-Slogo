package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.BackEndTurtle;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.ShowTurtleCommand;

public class ShowTurtleNode extends TurtleCommandNode {


  public ShowTurtleNode(int numParameters) {
    super(numParameters);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new ShowTurtleCommand());
    turtle.show();
    return 1;
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new ShowTurtleCommand());
      currTurtle.show();
      return 1;
    });
  }
}
