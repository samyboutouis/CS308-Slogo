package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.SetHeadingCommand;
import slogo.model.SlogoNode;

public class SetHeadingNode extends TurtleCommandNode {
  private Turtle turtle;
  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetHeadingNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

 /* @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    // only one value so not entirely necessary, could just do value = parameters.get(0).getReturnValue(commands)
    createMovement(commands);
    double prevHeading = turtle.getDirection();
    turtle.setDirection(values.get(0));
    return Math.abs(prevHeading - turtle.getDirection());
  }

  private void createMovement(List<Command> commands) {
    commands.add(new SetHeadingCommand(values.get(0)));
  }*/


  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,parameters, (currTurtle, values)->{
      currTurtle.addCommand(new SetHeadingCommand(values.get(0)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.setDirection(values.get(0));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
