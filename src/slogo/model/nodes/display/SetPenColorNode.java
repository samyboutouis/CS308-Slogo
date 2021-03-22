package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPenColorCommand;

public class SetPenColorNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetPenColorNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand( new SetPenColorCommand(values.get(0).intValue()));
      return values.get(0);
    });
  }
}
