package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.SetPositionCommand;

public class SetPositionNode extends TurtleCommandNode{
  private Turtle turtle;
  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetPositionNode(int numParameters, Turtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

  @Override
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
  }
}
