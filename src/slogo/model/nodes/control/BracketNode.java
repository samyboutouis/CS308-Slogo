package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;

/**
 * Represents any node that must read in brackets. Provides shared methods that will help those
 * nodes correctly read in brackets without duplicating methods.
 *
 * Any class that extends this class can leverage the implemented methods isFull() and
 * getFirstEnd().
 *
 *    int firstEnd = super.getFirstEnd();
 *
 * @author Felix Jiang
 */
public abstract class BracketNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private int brackets; // how many pairs of brackets to expect

  /**
   * Takes in how many bracket pairs are expected to parse this command.
   *
   * Sets up parameters list in order to calculate first end bracket.
   *
   * @param numParameters number of bracket pairs in this command.
   */
  public BracketNode(int numParameters) {
    super(numParameters);
    brackets = numParameters;
    parameters = super.getParameters();
  }

  /**
   *
   * @return true if node has received all the children it needs (dictated by if last bracket is a
   * list end bracket and the right amount of list end bracket nodes (value of brackets) have been
   * read.
   */
  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  /**
   * Used by subclasses to find index of the first list end bracket in the command.
   *
   * @return index of the first list end bracket, or -1 if not found.
   */
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
