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

public class IsPenDownNode extends TurtleCommandNode {
  private String mesg;
  private int ret;
  public IsPenDownNode(int parameter){
    super(parameter);
  }

 /* @Override
  public double getReturnValue(List<Command> commands) {
    if (turtle.isPenDown()){
    mesg = "Pen is down";
    ret  =1;
    } else {
      mesg = "Pen is up";
      ret = 0;
    }
    commands.add(new DisplayCommand(mesg));
    return ret;
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values)->{
      if (currTurtle.isPenDown()){
        //mesg = "Turtle "+currTurtle.getIndex()+" Pen is down";
        mesg = String.format("Turtle %d Pen is down", currTurtle.getIndex());
        ret  =1;
      } else {
        //mesg = "Pen is up";
        mesg = String.format("Turtle %d Pen is up", currTurtle.getIndex());
        ret = 0;
      }
      currTurtle.addCommand(new DisplayCommand(mesg));
      return ret;
    });
  }
}
