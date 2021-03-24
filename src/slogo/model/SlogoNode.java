package slogo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Top Level Abstraction of a node used to build the tree of commands corresponding to what
 * the user typed in.
 *
 * Each node represents a command, and its children are each of the parameters it takes in.
 * Recursively determine the output of children commands in getReturnValue, then calculate
 * its own return value, and then return it to its parent node. SlogoNodes will be traversed
 * using preorder traversal.
 *
 * All values typed in by the user map to a command using the ProgramParser. Each SlogoNode assumes
 * a correct number of parameters appear as its value in the properties file
 * src/resources/parameters/Commands.properties.
 *
 * SlogoNode itself only depends on its children SlogoNodes and assumes it is created correctly.
 *
 * If the user typed in: fd sum 50 40 bk 30
 * The SlogoNode tree(s) would be:
 *           fd         bk
 *         /          /
 *       sum        30
 *      /   \
 *     50   40
 *
 * SlogoNode subclasses are primarily created using reflection, as seen in NodeFactory.java
 *
 *      curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE).newInstance(parameters);
 *
 * Every subclass MUST override getReturnValue. Some subclasses that use brackets will override
 * isFull because they are full based on how many brackets have been seen.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public abstract class SlogoNode {

  private List<SlogoNode> parameters;
  private int numParameters;
  private String myString;
  private boolean BooleanNode = false;

  /**
   * Constructs a SlogoNode with an empty ArrayList representing its parameters. Sets the initial
   * value of parameters to the argument.
   *
   * @param numParameters number of parameters the command needs, or if isFull is overridden,
   *                      the number of bracket pairs the command has
   */
  public SlogoNode(int numParameters) {
    this.numParameters = numParameters;
    parameters = new ArrayList<>();
  }

  /**
   * Add a child node (one of this node's parameters) to this SlogoNode.
   *
   * @param node child node to be added
   */
  public void addNode(SlogoNode node) {
    parameters.add(node);
  }

  /**
   * Return the string of commands that originate from this SlogoNode.
   *
   * Calls getMyString() on each children in order to build the entire string
   * of commands and their parameters.
   *
   * @return String representing the entire command and all of its parameters for this SlogoNode
   */
  public String getMyString() {
    StringBuilder ret = new StringBuilder(myString);
    for (SlogoNode s : parameters) {
      ret.append(" ").append(s.getMyString());
    }
    return ret.toString();
  }

  /**
   * Set the string of this command, basically what value the user typed in to
   * correspond to this SlogoNode.
   *
   * Could have been included in our constructor since it applies to every node,
   * but it was added later on and did not get fixed during refactoring sessions.
   *
   * @param s what value the user typed in to represent this command, e.g. "sum", "fd",
   *          "setbg", "50"
   */
  public void setString(String s) {
    myString = s;
  }

  public void setBooleanNode(boolean flag) {
    BooleanNode = flag;
  }

  public boolean getBooleanNode() {
    return BooleanNode;
  }

  public boolean isFull() {
    return parameters.size() == numParameters;
  }

  protected List<SlogoNode> getParameters() {
    return parameters;
  }

  // for group node
  public int getNumParameters() {
    return numParameters;
  }

  // for group node to run command again with new parameters
  public void replaceParameters(List<SlogoNode> newParameters) {
    parameters.clear();
    // can't replace the instance since the subclasses point to same instance
    parameters.addAll(newParameters);
  }

  public abstract double getReturnValue(BackEndTurtleTracker tracker);
}

