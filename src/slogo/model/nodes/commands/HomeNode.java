package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.HomeCommand;

/**
 * Represents the node of the SlogoNode tree for a home command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class HomeNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a home node takes (0)
   */
  public HomeNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return distance turtle moved.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new HomeCommand());
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.home();
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
