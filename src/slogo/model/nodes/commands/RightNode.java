package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class RightNode extends CommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public RightNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    return values.get(0); // only one value for a Right node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(0, values.get(0)));
    // move Right the amount in values.get(0)
  }
}