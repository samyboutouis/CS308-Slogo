/**
 * Represents the node of the SlogoNode tree for a heading command
 *
 * @author Andre Wang
 */

package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class HeadingNode extends TurtleCommandNode {

  /**
   * constructor for heading node
   * @param parameter
   */
  public HeadingNode(int parameter) {
    super(parameter);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return returns the current direction of the current turtle in the tracker, also adds a new display
   * command to the current turtle in the tracker.
   */

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new DisplayCommand(
          "Turtle " + currTurtle.getIndex() + " Heading: " + currTurtle.getDirection()));
      return currTurtle.getDirection();
    });
  }
}