package slogo.model.nodes.display;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

/**
 * Represents the node of the SlogoNode tree for a get shape command.
 *
 * @author Felix Jiang
 */
public class GetShapeNode extends TurtleCommandNode {

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public GetShapeNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return shape index of turtle.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle
          .addCommand(new DisplayCommand("Turtle Shape Index is " + currTurtle.getShapeIndex()));
      return currTurtle.getShapeIndex();
    });
  }
}
