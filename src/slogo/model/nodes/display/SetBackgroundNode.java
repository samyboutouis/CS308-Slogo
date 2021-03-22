package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetBackgroundCommand;

public class SetBackgroundNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetBackgroundNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand( new SetBackgroundCommand(tracker.getSafe(), values.get(0).intValue()));
      return values.get(0);
    });
  }
}
