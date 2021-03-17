package slogo.model.nodes.queries;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.DisplayCommand;

public class IsPenDownNode extends SlogoNode {
  private Turtle turtle;
  private String mesg;
  private int ret;
  public IsPenDownNode(int parameter, BackEndTurtle turtle){
    super(parameter);
    this.turtle = turtle;
  }

  @Override
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
  }
}
