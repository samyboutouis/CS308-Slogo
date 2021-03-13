package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.turtlecommands.DegreeDirectionCommand;
import slogo.model.SlogoNode;

public class SetHeadingNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetHeadingNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    getValues(commands);
    createMovement(commands);
    return values.get(0);
  }

  private void createMovement(List<Command> commands) {
    commands.add(new DegreeDirectionCommand(values.get(0)));
  }

  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}
