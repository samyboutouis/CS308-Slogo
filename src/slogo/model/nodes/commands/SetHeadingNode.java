package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.turtlecommands.SetHeadingCommand;
import slogo.model.SlogoNode;

public class SetHeadingNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public SetHeadingNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    return values.get(0);
  }

  private void createMovement(List<Command> commands) {
    commands.add(new SetHeadingCommand(values.get(0)));
  }
}
