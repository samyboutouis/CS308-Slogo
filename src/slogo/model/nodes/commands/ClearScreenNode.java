package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.ClearScreenCommand;

/**
 * Represents the node of the SlogoNode tree for a clear screen command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ClearScreenNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a clear screen node takes (0)
   */
  public ClearScreenNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return how many pixels turtle moved
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new ClearScreenCommand());
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.clearScreen();
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
