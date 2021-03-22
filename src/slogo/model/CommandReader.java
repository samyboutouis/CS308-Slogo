package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import slogo.SafeBackEndTurtleTracker;
import slogo.model.nodes.control.MakeUserInstructionNode;

// creates parser, reads commands, and creates nodes
public class CommandReader {

  private static final String WHITESPACE = "\\s+";
  private static final String NEWLINE = "\n";
  private static final String RESOURCES_PACKAGE = "resources.";
  private static final String PARAMETERS_FILE = "parameters.Commands";
  private static final String PACKAGES_FILE = "packages.Packages";
  private static final String NODES_PATH = "slogo.model.nodes.";
  private ProgramParser parser;
  private Map<String, Double> variables;
  private Map<String, MakeUserInstructionNode> userDefinedCommands;
  private Map<String, String> userDefinedCommandsInString; // where you get the user specified functions to appear in UI.
  private List<Double> forTests;
  private ResourceBundle numParameters;
  private ResourceBundle packageName;
  private BackEndTurtleTracker tracker;
  private NodeFactory nodeFactory;

  public CommandReader(String language) {
    setLanguage(language);
    numParameters = ResourceBundle.getBundle(RESOURCES_PACKAGE + PARAMETERS_FILE);
    packageName = ResourceBundle.getBundle(RESOURCES_PACKAGE + PACKAGES_FILE);

    variables = new HashMap<>();
    forTests = new ArrayList<>();
    userDefinedCommands = new HashMap<>();
    userDefinedCommandsInString = new HashMap<>();
    tracker = new BackEndTurtleTracker(); // tracker doesn't have a turtle yet
    nodeFactory = new NodeFactory();
  }

  public SafeBackEndTurtleTracker parseInput(String input, BackEndTurtleTracker tracker)
      throws IllegalArgumentException {
    try {
      tracker.clearAllCommands();
      // we do not make assumption that tracker has empty commands list, but it should each time
      this.tracker = tracker;
      List<String> cleaned = cleanInput(input);
      List<SlogoNode> roots = buildTree(cleaned);
      makeCommands(roots);
    } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      throw new IllegalArgumentException(
          "Java Reflection Error: Unrecognized/Unimplemented Command");
    }
    return tracker;
  }

  public Map<String, Double> getVariables() {
    return variables;
  }

  // where you get the user specified functions to appear in UI.
  public Map<String, String> getUserDefinedCommandsInString() {
    return userDefinedCommandsInString;
  }

  public Map<String, String> getCommands() {
    return userDefinedCommandsInString;
  }

  public void setLanguage(String language) {
    parser = new ProgramParser();
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
  }

  // used to test return values
  public List<Double> testParseInput(String input) {
    forTests = new ArrayList<>();
    tracker.deleteAllData();
    tracker.addTurtle(new BackEndTurtle(0.0, 0.0, 0.0, true, true, 0));
    parseInput(input, tracker);
    return forTests;
  }

  private List<SlogoNode> buildTree(List<String> cleaned)
      throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, IllegalArgumentException {
    Stack<SlogoNode> st = new Stack<>();
    List<SlogoNode> roots = new ArrayList<>();
    SlogoNode curr = null;
    for (String s : cleaned) {
      String symbol = parser.getSymbol(s);
      if (symbol.equals("NO MATCH")) {
        throw new IllegalArgumentException("Input syntax is incorrect: " + s);
      }
      Class<?> node = Class
          .forName(NODES_PATH + packageName.getString(symbol) + "." + symbol + "Node");
      int parameters = Integer.parseInt(numParameters.getString(symbol));
      curr = nodeFactory.getNode(symbol, s, node, parameters, variables, userDefinedCommands, curr,
          userDefinedCommandsInString);

      if (curr == null) {
        continue;
      }

      handleStack(st, roots, curr);
    }
    return roots;
  }

  private void handleStack(Stack<SlogoNode> st, List<SlogoNode> roots, SlogoNode curr) {
    if (curr.isFull()) { // only true if node has no parameters
      if (st.isEmpty()) {
        roots.add(curr);
      } else {
        st.peek().addNode(curr);
      }
    } else { // curr not full
      if (st.isEmpty()) {
        roots.add(curr);
      }
      st.push(curr);
    }
    // add to roots whenever stack is empty, so we can pop off stack without worrying about adding to root
    // each thing on the stack is essentially the previous (lower) nodes' left most unfilled children
    popAllFullChildren(st);
  }

  private void popAllFullChildren(Stack<SlogoNode> st) {
    while (!st.isEmpty() && st.peek().isFull()) {
      SlogoNode top = st.pop();
      if (!st.isEmpty()) {
        st.peek().addNode(top);
      }
    }
  }

  // each command will add objects to tracker
  // we keep track of values only for testing purposes
  private void makeCommands(List<SlogoNode> roots) {
    for (SlogoNode root : roots) {
      double value = root.getReturnValue(tracker);
      forTests.add(value);
    }
  }

  // removes comments from input and white space between values
  private List<String> cleanInput(String input) {
    String[] preCleaned = input.split(NEWLINE);
    List<String> cleaned = new ArrayList<>();
    for (String line : preCleaned) {
      if (line.trim().equals("")) {
        continue; // ignores lines that are only new lines
      }
      if (!parser.getSymbol(line).equals("Comment")) {
        cleaned.addAll(Arrays.asList(line.trim().split(WHITESPACE)));
      }
    }
    return cleaned;
  }
}
