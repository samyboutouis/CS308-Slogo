package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetPositionCommand;

public class SetPositionNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetPositionNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetPositionCommand(values.get(0), values.get(1)));
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.setXY(values.get(0), values.get(1));
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }

 /* @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    double prevX = turtle.getX();
    double prevY = turtle.getY();
    turtle.setXY(values.get(0), values.get(1));
    return Math.sqrt(Math.pow(prevX - turtle.getX(), 2) +  Math.pow(prevY - turtle.getY(), 2));
  }

  private void createMovement(List<Command> commands) {
    commands.add(new SetPositionCommand(values.get(0), values.get(1)));
  }*/


}
