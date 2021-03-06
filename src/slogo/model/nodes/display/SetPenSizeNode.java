package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPenSizeCommand;

public class SetPenSizeNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetPenSizeNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetPenSizeCommand(values.get(0)));
      return values.get(0);
    });
  }
}
