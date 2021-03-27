package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.ShowTurtleCommand;

/**
 * Represents the node of the SlogoNode tree for a show command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ShowTurtleNode extends TurtleCommandNode {

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a show node takes (0)
   */
  public ShowTurtleNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 1 since turtle is shown.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new ShowTurtleCommand());
      currTurtle.show();
      return 1;
    });
  }
}
