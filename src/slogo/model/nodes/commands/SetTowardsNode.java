package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.turtlecommands.SetTowardsCommand;
import slogo.model.SlogoNode;

public class SetTowardsNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetTowardsNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    getValues(commands);
    createMovement(commands);
    return values.get(0); // FIX
  }

  private void createMovement(List<Command> commands) {
    commands.add(new SetTowardsCommand(values.get(0), values.get(1)));
  }

  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}
