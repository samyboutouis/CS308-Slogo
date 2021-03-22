package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.control.BracketNode;
import slogo.model.nodes.control.ListEndNode;

public class TellNode extends BracketNode {

  private List<SlogoNode> parameters;

  public TellNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    int ret = 0;
    List<Integer> tellTurtleList = new ArrayList<>();
    for (int i = 1; i < parameters.size() - 1; i++) {
      ret = (int) parameters.get(i).getReturnValue(tracker); // should all be constant nodes
      tellTurtleList.add(ret);
    }
    tracker.setTellList(tellTurtleList);
    return ret;
  }

}
