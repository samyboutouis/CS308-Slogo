package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

// second level of abstraction for all command nodes that may need getValues
public abstract class TurtleCommandNode extends SlogoNode {

  public TurtleCommandNode(int numParameters) {
    super(numParameters);
  }

  protected double loopThroughTurtles(BackEndTurtleTracker tracker, List<SlogoNode> parameters, TurtleAction action) {
    Iterator<Integer> itr = tracker.getIterator();
    double ret = 0.0;
    while(itr.hasNext()){
      int index = itr.next();
      tracker.setCurr(index);
      BackEndTurtle currTurtle = tracker.getTurtle(index);
      ret = action.turtleAction(currTurtle, getValues(tracker, parameters));
    }
    return ret;
  }

  private List<Double> getValues(BackEndTurtleTracker tracker, List<SlogoNode> parameters) {
    List<Double> values = new ArrayList<>();
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(tracker)); // runs on current turtle that was just set in while loop
    }
    return values;
  }

  // OLD getValues Method
  // for subclasses to get their parameter values
  // gets values for all parameters of node that calls this method, needs commands list to
  // create the commands when the parameters call getReturnValue
//  protected List<Double> getValues(List<Command> commands, List<SlogoNode> parameters) {
//    List<Double> values = new ArrayList<>();
//    for(SlogoNode node : parameters) {
//      values.add(node.getReturnValue(commands));
//    }
//    return values;
//  }
}
