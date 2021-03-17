package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class ForwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;
  private Turtle turtle;

  public ForwardNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    turtle.forward(values.get(0));
    createMovement(commands);
    return values.get(0); // only one value for a forward node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(values.get(0), 0));
    // move forward the amount in values.get(0)
  }
}
