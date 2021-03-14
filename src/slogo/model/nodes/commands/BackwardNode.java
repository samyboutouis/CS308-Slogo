package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class BackwardNode extends CommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public BackwardNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    return values.get(0); // only one value for a Back node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(-1 * values.get(0), 0));
    // move Back the amount in values.get(0)
  }
}
