package slogo.model.nodes.commands;

import java.util.List;
import slogo.BackEndTurtle;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class BackwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;
  private Turtle turtle;

  public BackwardNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.back(values.get(0));
      currTurtle.addCommand(new MovementCommand(-1 * values.get(0), 0));
      return values.get(0); // only one value for a Back node
    });
  }
}
