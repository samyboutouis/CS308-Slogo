package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a left command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class LeftNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a left node takes (1)
   */
  public LeftNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return degrees turtle moved in the left direction.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.rotate(-1 * values.get(0));
      currTurtle.addCommand(new MovementCommand(0, -1 * values.get(0)));
      return values.get(0);
    });
  }
}