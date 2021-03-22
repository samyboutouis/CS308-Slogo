package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.control.ListEndNode;

public class TellNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private int brackets;

  public TellNode(int parameter) {
    super(parameter);
    brackets = parameter;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof ListEndNode;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    int ret = 0;
    tracker
        .clearActiveTurtles(); // clear the previous active list of turtles, to prepare room for new list of active turtles.
    List<Integer> tellTurtleList = new ArrayList<>();
    for (int i = 1; i < parameters.size() - 1; i++) {
      ret = (int) parameters.get(i).getReturnValue(tracker); // should all be constant nodes
      tellTurtleList.add(ret);
    }
    tracker.setTellList(tellTurtleList);
    return ret;
  }

}
