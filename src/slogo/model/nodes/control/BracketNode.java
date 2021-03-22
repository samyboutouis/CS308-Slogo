package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;

public abstract class BracketNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private int brackets; // how many pairs of brackets to expect

  public BracketNode(int numParameters) {
    super(numParameters);
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  protected int getFirstEnd() {
    for (int i = 0; i < parameters.size(); i++) {
      if (parameters.get(i) instanceof ListEndNode) {
        return i;
      }
    }
    return -1; // shouldn't ever reach here
  }

  // check to see if we've seen brackets number of list end nodes
  private boolean checkBrackets() {
    int seen = 0;
    for (SlogoNode node : parameters) {
      if (node instanceof ListEndNode) {
        seen++;
      }
    }
    return seen == brackets;
  }
}
