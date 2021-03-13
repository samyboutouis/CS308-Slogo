package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class ForwardNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public ForwardNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    getValues(commands);
    createMovement(commands);
    return values.get(0); // only one value for a forward node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(values.get(0), 0));
    // move forward the amount in values.get(0)
  }

  // gets values for all parameters of this node, needs commands list to create the commands when
  // the parameters call getReturnValue
  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}
