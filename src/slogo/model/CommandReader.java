package slogo.model;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.RepeatNode;
import slogo.model.nodes.control.VariableNode;

// creates parser, reads commands, and creates nodes
public class CommandReader {
  private static final String WHITESPACE = "\\s+";
  private static final String NEWLINE = "\n";
  private static final String RESOURCES_PACKAGE ="resources.";
  private static final String PARAMETERS_FILE = "parameters.Commands";
  private static final String PACKAGES_FILE = "packages.Packages";
  private ProgramParser parser;
  private Map<String, Double> variables;
  private Map<String, List<SlogoNode>> userDefinedCommands;
  private List<Double> forTests;
  private ResourceBundle numParameters;
  private ResourceBundle packageName;
  private List<Command> commands;

  public CommandReader(String language) {
    setLanguage(language);
    numParameters = ResourceBundle.getBundle(RESOURCES_PACKAGE + PARAMETERS_FILE);
    packageName = ResourceBundle.getBundle(RESOURCES_PACKAGE + PACKAGES_FILE);

    commands = new ArrayList<>();
    variables = new HashMap<>();
    forTests = new ArrayList<>();
  }

  public List<Command> parseInput(String input) throws IllegalArgumentException{
    commands.clear();
    try {
      List<String> cleaned = cleanInput(input);
      List<SlogoNode> roots = buildTree(cleaned);
      makeCommands(roots);
    } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
    }
    return commands;
  }

  public Map<String, Double> getVariables() {
    return variables;
  }

  public void setLanguage(String language) {
    parser = new ProgramParser();
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
  }

  // used to test return values
  public List<Double> testParseInput(String input) {
    forTests = new ArrayList<>();
    parseInput(input);
    return forTests;
  }

  private List<SlogoNode> buildTree(List<String> cleaned)
      throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Stack<SlogoNode> st = new Stack<>();
    List<SlogoNode> roots = new ArrayList<>();
    for(String s : cleaned){
      String symbol = parser.getSymbol(s);
      if(symbol.equals("NO MATCH")){
        throw new IllegalArgumentException("Input syntax is incorrect");
      }
      Class<?> node = Class.forName("slogo.model.nodes." + packageName.getString(symbol) + "." + symbol + "Node");

      SlogoNode curr;
      int parameters = Integer.parseInt(numParameters.getString(symbol));
      switch(symbol){
        // handle separately: Constant, Variable
        case "Constant" -> {
          curr = new ConstantNode(parameters, Double.parseDouble(s));
          // if stack is empty and we see a constant, it doesn't do anything to the program but
          // we still add it to the tree
        }
        case "Variable" -> {
          curr = new VariableNode(parameters, variables, s); // s is the value we read, symbol is the classification
        }
        case "Repeat" -> {
          // needs the map of variables in constructor to add repcount variable
          curr = new RepeatNode(parameters, variables);
        }
        case "Home", "ClearScreen", "SetTowards", "SetPosition", "" -> {
          // curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE, turtle).newInstance(parameters, turtle);
        }
        default -> {
          curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE).newInstance(parameters);
        }
      }
      if(curr.isFull()){ // only true if node has no parameters
        if(st.isEmpty()) {
          roots.add(curr);
        }
        else{
          st.peek().addNode(curr);
        }
      }
      else if(st.isEmpty()){
        roots.add(curr);
        st.push(curr);
      }
      else{ // curr not full and stack not empty
        st.push(curr);
      }
      while(!st.isEmpty() && st.peek().isFull()){
        SlogoNode top = st.pop();
        if(!st.isEmpty()){
          st.peek().addNode(top);
        }
      }
    }
    return roots;
  }

  private void makeCommands(List<SlogoNode> roots){
    for(SlogoNode root : roots){
      double value = root.getReturnValue(commands);
      forTests.add(value);
    }
  }

  // removes comments from input and white space between values
  private List<String> cleanInput(String input) throws IllegalArgumentException{
    String[] preCleaned = input.split(NEWLINE);
    List<String> cleaned = new ArrayList<>();
    for(String line : preCleaned){
      if(line.trim().equals("")){
        continue; // ignores lines that are only new lines
      }
      if (!parser.getSymbol(line).equals("Comment")){
        cleaned.addAll(Arrays.asList(line.trim().split(WHITESPACE)));
      }
    }
    return cleaned;
  }
}
