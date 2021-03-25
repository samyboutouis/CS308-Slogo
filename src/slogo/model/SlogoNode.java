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

  /**
   * Indicates if this SlogoNode is a node that implements a boolean command.
   * Subclass constructor will call this method if it is a boolean node.
   *
   * Default value of BooleanNode is false, so non-boolean node subclasses won't call this method.
   *
   * @param flag true if this node is a boolean node, false otherwise
   */
  public void setBooleanNode(boolean flag) {
    BooleanNode = flag;
  }

  /**
   * Return if this node is a boolean node. Solely used in askwith command to make sure
   * the expression is only a boolean node, although the code will parse regardless, it is
   * more of a warning to the user on how to correctly use the language.
   *
   * @return true if node is a boolean node, false otherwise
   */
  public boolean getBooleanNode() {
    return BooleanNode;
  }

  /**
   * Lets the tree builder methods in CommandReader know if this node's parameters/commands within
   * brackets have all been read, so the node can be popped off the stack and added to its parent
   * or be the next root in the list of SlogoNodes in CommandReader.
   *
   * Method is overridden by nodes that have commands which use brackets rather than simple
   * parameters.
   *
   * @return true if current node has all its children parsed, false otherwise
   */
  public boolean isFull() {
    return parameters.size() == numParameters;
  }

  /**
   * Protected getter used by subclasses to access same list of parameters that super class
   * has access to.
   *
   * Any node that has parameters calls this method in its constructor, or one of its
   * intermediate superclass constructors.
   *
   * @return list that will hold all parameters for this node
   */
  protected List<SlogoNode> getParameters() {
    return parameters;
  }

  /**
   * Returns the number of parameters this node takes in. Used by the GroupStartNode which
   * implements the grouping feature. Needs to know this value in order to see if grouping
   * contains the correct multiple of parameters.
   *
   * numParameters sometimes also refers to the number of bracket pairs a command (e.g. repeat,
   * for, dotimes, if...) takes in. This method still returns that value to GroupStartNode,
   * but behavior is unknown, although allowable to the user.
   *
   * @return number of parameters or bracket pairs the command this node represents uses
   */
  public int getNumParameters() {
    return numParameters;
  }

  /**
   * Replace the values in parameters with the values in newParameters. Makes sure to only
   * replace the values and not the entire reference, since the subclass still points
   * to the same list.
   *
   * Used by GroupStartNode to allow multiple parameters to be called on a command node
   * without creating more command nodes.
   *
   * @param newParameters new list of parameters for the subclass to run getReturnValue(tracker)
   */
  public void replaceParameters(List<SlogoNode> newParameters) {
    parameters.clear();
    // can't replace the instance since the subclasses point to same instance
    parameters.addAll(newParameters);
  }

  /**
   * Calculates the value of the command and returns it to the parent node. Subclasses will
   * always provide the implementation (thus it is an abstract method). Unless the node is a
   * variable, constant, or has no parameters, the getReturnValue will always call the children
   * getReturnValue first to get the parameters before returning its own, similar to a pre order
   * traversal.
   *
   * The backendTurtleTracker must be passed to each children in case one of the child commands
   * will need it to calculate turtle position or add a command. Some methods won't directly
   * use this object.
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return the output value of executing this command, e.g. sum 50 50 has return value 100
   */
  public abstract double getReturnValue(BackEndTurtleTracker tracker);
}

