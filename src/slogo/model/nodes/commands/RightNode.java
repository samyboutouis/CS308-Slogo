package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class RightNode extends TurtleCommandNode {

  private Turtle turtle;
  private List<SlogoNode> parameters;
  private List<Double> values;

  public RightNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }
/* old getReturnValue method.
  @Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    turtle.rotate(values.get(0));
    return values.get(0); // only one value for a Right node
  }

  private void createMovement(List<Command> commands) {
    commands.add(new MovementCommand(0, values.get(0)));
    // move Right the amount in values.get(0)
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,parameters, (currTurtle, values)->{
      currTurtle.rotate(values.get(0));
      currTurtle.addCommand(new MovementCommand(0, values.get(0)));
      return values.get(0);
    });
  };
}