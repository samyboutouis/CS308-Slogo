package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPenColorCommand;

/**
 * Represents the node of the SlogoNode tree for a set pen color command.
 *
 * @author Felix Jiang
 */
public class SetPenColorNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public SetPenColorNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of new index that we set the turtle pen color to.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetPenColorCommand(values.get(0).intValue()));
      currTurtle.setPenColorIndex(values.get(0).intValue());
      return values.get(0);
    });
  }
}
