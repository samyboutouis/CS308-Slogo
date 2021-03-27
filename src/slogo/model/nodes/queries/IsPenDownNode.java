/**
 * Represents the node of the SlogoNode tree for a ispendown command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class IsPenDownNode extends TurtleCommandNode {

  private String mesg;
  private int ret;

  /**
   * constructor for ispendown node
   * @param parameter
   */
  public IsPenDownNode(int parameter) {
    super(parameter);
  }


  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 1 if the pen is down, 0 else, also adds a customized display command to the current turtle
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      if (currTurtle.isPenDown()) {
        //mesg = "Turtle "+currTurtle.getIndex()+" Pen is down";
        mesg = String.format("Turtle %d Pen is down", currTurtle.getIndex());
        ret = 1;
      } else {
        //mesg = "Pen is up";
        mesg = String.format("Turtle %d Pen is up", currTurtle.getIndex());
        ret = 0;
      }
      currTurtle.addCommand(new DisplayCommand(mesg));
      return ret;
    });
  }
}
