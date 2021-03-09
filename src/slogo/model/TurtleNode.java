package slogo.model;

import java.util.List;
import slogo.Command;
import slogo.MovementCommand;
import slogo.Turtle;

public class TurtleNode extends SlogoNode{
  private String type;
  private List<SlogoNode> parameters;
  private List<Double> values;

  public TurtleNode(int numParameters, String type){
    super(numParameters);
    this.type = type;
    parameters = super.getParameters();
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    getValues(commands);
    switch(type){
      case "fd" -> {
        createMovement(commands);
        return values.get(0);
      }
    }
    return 0;
  }

  @Override
  protected void createMovement(List<Command> commands) {
    switch(type){
      case "fd" -> {
        commands.add(new MovementCommand(values.get(0), 0));
      }
      case "bk" -> {
        commands.add(new MovementCommand(-1 * values.get(0), 0));
      }
    }
  }

  private void getValues(List<Command> commands) {
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
  }
}
