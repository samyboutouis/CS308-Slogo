package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.control.BracketNode;

/**
 * Represents the node of the SlogoNode tree for a tell command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class TellNode extends BracketNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for tell node.
   *
   * @param numParameters amount of bracket pairs a tell command takes (1)
   */
  public TellNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return id of last turtle in the tell list.
   */
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
