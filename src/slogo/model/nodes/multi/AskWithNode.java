package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.ListEndNode;
import slogo.turtlecommands.TellCommand;

/**
 * Represents the node of the SlogoNode tree for an ask with command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class AskWithNode extends AskNode {

  private List<SlogoNode> parameters;
  private int brackets;

  public AskWithNode(int numParameters) {
    super(numParameters);
    brackets = numParameters;
    parameters = super.getParameters();
  }

  // gets all the turtles that the ask command wants
  // can't use existing lambdas since we want to ONLY have one active turtle when checking the condition
  // tellList should hold values already so we can wipe out active list each time
  @Override
  protected List<Integer> getAskTurtles(BackEndTurtleTracker tracker) {
    // ask with has exact same setup as ask, except the way we get the turtles is by checking expression, so I just overrode this one method
    // protected is still called by askNode, and it knows that this method has been overridden, even though askNode also has implementation
    List<Integer> ret = new ArrayList<>();
    try {
      assert parameters.get(1).getBooleanNode();
    } catch (ClassCastException e) {
      System.out.println("Not a Boolean Expression in AskWith");
    }
    for (Integer i : tracker.getAllTurtles()) {
      tracker.checkOneTurtle(i);
      if (parameters.get(1).getReturnValue(tracker) != 0) {
        ret.add(i);
      }
    }
    return ret;
  }
}

