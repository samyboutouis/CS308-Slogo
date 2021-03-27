package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenDownCommand;

/**
 * Represents the node of the SlogoNode tree for a pen down command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenDownNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a pen down node takes (0)
   */
  public PenDownNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 1 since pen is down.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new PenDownCommand());
      currTurtle.penDown();
      return 1;
    });
  }
}
