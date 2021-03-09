package slogo.model;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.MovementCommand;

public class TurtleNode extends SlogoNode{
  private List<SlogoNode> parameters;
  private List<Double> values;

  public TurtleNode(int numParameters, String type){
    super(numParameters, type);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    getValues(commands);
    switch(super.getType()){
      case "Forward", "Backward", "Left", "Right" -> {
        createMovement(commands);
        return values.get(0);
      }
    }
    return 0;
  }

  private void createMovement(List<Command> commands) {
    switch(super.getType()){
      case "Forward" -> {
        commands.add(new MovementCommand(values.get(0), 0));
      }
      case "Backward" -> {
        commands.add(new MovementCommand(-1 * values.get(0), 0));
      }
      case "Left" -> {
        commands.add(new MovementCommand(0, -1 * values.get(0)));
      }
      case "Right" -> {
        commands.add(new MovementCommand(0, values.get(0)));
      }
    }
  }

  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}
