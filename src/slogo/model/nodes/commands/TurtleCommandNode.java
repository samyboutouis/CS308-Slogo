package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import slogo.model.BackEndTurtle;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

// second level of abstraction for all command nodes that affect individual turtles
public abstract class TurtleCommandNode extends SlogoNode {

  public TurtleCommandNode(int numParameters) {
    super(numParameters);
  }

  protected double loopThroughTurtles(BackEndTurtleTracker tracker, List<SlogoNode> parameters,
      TurtleAction action) {
    Iterator<Integer> itr = tracker.getIterator();
    double ret = 0.0;
    while (itr.hasNext()) {
      int id = itr.next();
      tracker.setCurr(id);
      BackEndTurtle currTurtle = tracker.getTurtle(id);
      ret = action.turtleAction(currTurtle, getValues(tracker, parameters));
    }
    return ret;
  }

  private List<Double> getValues(BackEndTurtleTracker tracker, List<SlogoNode> parameters) {
    List<Double> values = new ArrayList<>();
    for (SlogoNode node : parameters) {
      values.add(node.getReturnValue(tracker));
      // runs on current turtle that was just set in while loop
    }
    return values;
  }
}
