/**
 * Represents the node of the SlogoNode tree for a ycoordinate command.
 *
 * @author Andre Wang
 */
package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class YCoordinateNode extends TurtleCommandNode {

  /**
   * constrcutro for ycoordinate node
   * @param parameter
   */
  public YCoordinateNode(int parameter) {
    super(parameter);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return adds a display command, returns the current turtle's current y position
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      String mesg = String
          .format("Turtle %d Y Coordinate is %.2f", currTurtle.getIndex(), currTurtle.getY());
      System.out.println(mesg);
      currTurtle.addCommand(new DisplayCommand(mesg));
      return currTurtle.getY();
    });
  }
}
