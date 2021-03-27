/**
 * Represents the node of the SlogoNode tree for a isshowing node command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class IsShowingNode extends TurtleCommandNode {

  private String mesg;
  private int ret;

  /**
   * constructor for isshowing node
   * @param parameter
   */
  public IsShowingNode(int parameter) {
    super(parameter);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 1 if the turtle is shown, else 0. adds a customized display command to the current turtle.
   */

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      if (currTurtle.isShowing()) {
        //mesg = "Turtle is shown";
        mesg = String.format("Turtle %d is shown", currTurtle.getIndex());
        ret = 1;
      } else {
        mesg = String.format("Turtle %d is hidden", currTurtle.getIndex());
        ret = 0;
      }
      currTurtle.addCommand(new DisplayCommand(mesg));
      return ret;
    });
  }
}
