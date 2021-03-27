package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetShapeCommand;

/**
 * Represents the node of the SlogoNode tree for a set shape command.
 *
 * @author Felix Jiang
 */
public class SetShapeNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public SetShapeNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of index that we set the turtle shape to.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetShapeCommand(values.get(0).intValue()));
      currTurtle.setShapeIndex(values.get(0).intValue());
      return values.get(0);
    });
  }
}
