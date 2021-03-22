package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.control.BracketNode;
import slogo.model.nodes.control.ConstantNode;

public class AskNode extends BracketNode {

  private List<SlogoNode> parameters;
  private int firstEnd;

  public AskNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  // gets all the turtles that the ask command wants
  protected List<Integer> getAskTurtles(BackEndTurtleTracker tracker) {
    List<Integer> ret = new ArrayList<>();
    for (int i = 1; i < firstEnd; i++) {
      try {
        int turtleId = (int) ((ConstantNode) parameters.get(i)).getReturnValue(tracker);
        // should all be constant nodes
        ret.add(turtleId);
      } catch (ClassCastException e) {
        System.out.println("Ask Turtle Format Incorrect!");
      }
    }
    return ret;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    firstEnd = super.getFirstEnd();
    List<Integer> askTurtleList = getAskTurtles(tracker);
    tracker.setAskList(askTurtleList); // creates the tellCommand
    double ret = 0;
    for (int i = firstEnd + 2; i < parameters.size() - 1; i++) {
      ret = parameters.get(i).getReturnValue(tracker);
    }
    tracker.revertAskList(); // creates the tellCommand
    return ret;
  }
}
