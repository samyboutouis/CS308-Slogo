package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class BackwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;
  private Turtle turtle;

  public BackwardNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    turtle.back(values.get(0));
    return values.get(0); // only one value for a Back node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(-1 * values.get(0), 0));
    // move Back the amount in values.get(0)
  }
}
