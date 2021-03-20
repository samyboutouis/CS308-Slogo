package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetHeadingCommand;
import slogo.model.SlogoNode;

public class SetHeadingNode extends TurtleCommandNode {
  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetHeadingNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
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
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,parameters, (currTurtle, values)->{
      currTurtle.addCommand(new SetHeadingCommand(values.get(0)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.setDirection(values.get(0));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
