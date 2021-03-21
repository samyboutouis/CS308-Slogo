package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPaletteCommand;

public class SetPaletteNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetPaletteNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      for (Double v : values) {
        if (v.intValue() > 255 || v.intValue() < 0) {
          throw new IllegalArgumentException(
              "Set Palette RGB Value Error - Not within range [0, 255]");
        }
      }
      currTurtle.addCommand(new SetPaletteCommand(tracker.getSafe(), values.get(0).intValue(),
          values.get(1).intValue(), values.get(2).intValue(), values.get(3).intValue()));
      return values.get(0);
    });
  }
}