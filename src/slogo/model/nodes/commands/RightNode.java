package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class RightNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public RightNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    getValues(commands);
    createMovement(commands);
    return values.get(0); // only one value for a Right node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(0, values.get(0)));
    // move Right the amount in values.get(0)
  }

  // gets values for all parameters of this node, needs commands list to create the commands when
  // the parameters call getReturnValue
  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}