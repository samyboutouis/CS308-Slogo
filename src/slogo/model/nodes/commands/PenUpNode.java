package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenUpCommand;

/**
 * Represents the node of the SlogoNode tree for a pen up command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenUpNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a pen up node takes (0)
   */
  public PenUpNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 0 since pen is up.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new PenUpCommand());
      currTurtle.penUp();
      return 0;
    });
  }
}
