package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPaletteCommand;

/**
 * Represents the node of the SlogoNode tree for a set palette command.
 *
 * @author Felix Jiang
 */
public class SetPaletteNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public SetPaletteNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of index we added/updated on the palette.
   */
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