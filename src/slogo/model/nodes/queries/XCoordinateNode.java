/**
 * Represents the node of the SlogoNode tree for a xccordinate command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class XCoordinateNode extends TurtleCommandNode {

  /**
   * constructor for xccordinate node
   * @param parameter
   */
  public XCoordinateNode(int parameter) {
    super(parameter);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return return the current x position of the current turtle, adds a display command to the current turtle.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      String mesg = String
          .format("Turtle %d X Coordinate is %.2f", currTurtle.getIndex(), currTurtle.getX());
      //System.out.println(mesg);
      currTurtle.addCommand(new DisplayCommand(mesg));
      return currTurtle.getX();
    });
  }
}
