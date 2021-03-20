package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class IsShowingNode extends TurtleCommandNode {
  private String mesg;
  private int ret;
  public IsShowingNode(int parameter){
    super(parameter);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {

    if (turtle.isShowing()){
      mesg = "Turtle is shown";
      ret  =1;
    } else {
      mesg = "Turtle is hidden";
      ret = 0;
    }
    commands.add(new DisplayCommand(mesg));
    return ret;
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values)->{
      if (currTurtle.isShowing()){
        //mesg = "Turtle is shown";
        mesg = String.format("Turtle %d is shown", currTurtle.getIndex());
        ret  =1;
      } else {
        mesg = String.format("Turtle %d is hidden", currTurtle.getIndex());
        ret = 0;
      }
      currTurtle.addCommand(new DisplayCommand(mesg));
      return ret;
    });
  }
}
