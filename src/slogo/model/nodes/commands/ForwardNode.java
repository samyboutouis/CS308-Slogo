package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class ForwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public ForwardNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    return values.get(0); // only one value for a forward node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(values.get(0), 0));
    // move forward the amount in values.get(0)
  }
}
