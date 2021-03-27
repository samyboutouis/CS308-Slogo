package slogo.model.nodes.display;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

/**
 * Represents the node of the SlogoNode tree for a get pen color command.
 *
 * @author Felix Jiang
 */
public class GetPenColorNode extends TurtleCommandNode {

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public GetPenColorNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of pen color index of turtle.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle
          .addCommand(new DisplayCommand("Pen Color Index is " + currTurtle.getPenColorIndex()));
      return currTurtle.getPenColorIndex();
    });
  }
}
