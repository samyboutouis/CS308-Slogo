package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.HideTurtleCommand;

/**
 * Represents the node of the SlogoNode tree for a hide command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class HideTurtleNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a hide turtle node takes (0)
   */
  public HideTurtleNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 0 since turtle is hidden.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new HideTurtleCommand());
      currTurtle.hide();
      return 0;
    });
  }
}