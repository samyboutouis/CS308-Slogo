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

/**
 * Public API of the BackEnd Model. Creates the parser and reads commands sent from the FrontEnd.
 *
 * First takes in uncleaned string that user typed in and separates by newline in order to
 * ignore any comments. Then, separates each line by whitespace to pass into the buildTree
 * method that constructs the tree of SlogoNodes. Finally, return a SafeBackEndTurtleTracker
 * which contains information regarding Command objects the FrontEnd should run on its
 * turtles.
 *
 * This class also provides the front end with a map of variables and a map of user defined
 * commands to be displayed for the user. A language must be provided on construction, but
 * the frontEnd can call setLanguage(language) to change the language of what the CommandReader
 * should read in.
 *
 * Delegates task of creating nodes to the NodeFactory.
 *
 * Also provides JUnit tests with a way to monitor output values, through the
 * testParseInput(input) method.
 *
 * Assumes user typed in string that separates each item by whitespace, otherwise this class would
 * not be able to parse the input.
 *
 * Depends on the ProgramParser class and NodeFactory class. The BackEndTurtleTracker is indirectly
 * depended on in this class because it is needed to be passed through for each getReturnValue
 * method call.
 *
 *      CommandReader commandReader = new CommandReader(language);
 *      commandReader.parseInput(program, backEndTurtleTracker); // program represents user program
 *      // returns a SafeBackEndTurtleTracker
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
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

  /**
   * Creates a CommandReader object with an initial language.
   *
   * Sets up resource bundles in order to read the package names of nodes and the number of
   * parameters or bracket pairs. Initializes all instance variables, including the
   * BackEndTurtleTracker, which is used for testing purposes, since FrontEnd will send a new
   * turtleTracker for each parseInput call.
   *
   * @param language language corresponding to property file name for reading in commands of
   *                 different languages.
   */
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

  /**
   * Parse a string input from the user in order to return an object that holds the Command
   * objects that will be run on the FrontEnd turtles.
   *
   * @param input exact string that user typed in to terminal
   * @param tracker tracker representing all current turtles on the screen and their position,
   *                orientation, pen color index, and shape index.
   * @return object containing all commands that should be run on each turtle. obtained from
   * the method getAllTurtleCommands(), which returns a map of turtle ID to list of commands
   * it should run.
   * @throws IllegalArgumentException Represents any kind of parsing error that is encountered
   * by this command reader. Message is displayed by front end to alert the user of the error
   * in their written program.
   */
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

  /**
   * Gives map of variable names and values to front end for display.
   *
   * @return map of variable name and their value.
   */
  public Map<String, Double> getVariables() {
    return variables;
  }

  /**
   * Gives map of user specified commands and the logic they typed in to front end for display.
   *
   * @return map of user specified command names and the rest of the command they typed in.
   */
  public Map<String, String> getUserDefinedCommandsInString() {
    return userDefinedCommandsInString;
  }

  /**
   * Sets the language of the reader. The reader will create a new instance of the ProgramParser
   * with a new pattern representing the language the user wants to read, as well as the original
   * Syntax properties for recognizing the kind of string the user has typed in for each string.
   *
   * @param language string representing the new language the user wants to type in.
   */
  public void setLanguage(String language) {
    parser = new ProgramParser();
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
  }

  /**
   * Similar functionality as the parseInput() method, except solely used for testing if the return
   * values of the SLogo commands are accurate.
   *
   * Does not make the assumption that multiple calls will not be made in the same test, so this
   * method will clear the tracker data for each subsequent call.
   *
   * Initializes the forTests array used to keep track of all return values from the SLogo commands.
   * forTests is updated in the private method makeCommands().
   *
   * For example, an input of: sum 20 30 fd 40 would return a list of {50.0, 40.0}
   *
   * @param input string that the tester would like to test, represents a SLogo program
   * @return value of each command output from the test in a list.
   */
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
