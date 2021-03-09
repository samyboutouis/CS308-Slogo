package slogo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import slogo.Command;

// creates parser, reads commands, and creates nodes
public class CommandReader {
  private static final String WHITESPACE = "\\s+";
  private static final String NEWLINE = "\n";
  private ProgramParser parser;
  private Map<String, Double> variables;

  private List<Command> commands;

  public CommandReader(String language) {
    parser = new ProgramParser();
    parser.addPatterns(language);
    parser.addPatterns("Syntax");

    commands = new ArrayList<>();
    variables = new HashMap<>();
  }

  public List<Command> parseInput(String input) throws IllegalArgumentException{
    commands.clear();
    List<String> cleaned = cleanInput(input);
    List<SlogoNode> roots = buildTree(cleaned);
    makeCommands(roots);

    return commands;
  }

  private List<SlogoNode> buildTree(List<String> cleaned){
    Stack<SlogoNode> st = new Stack<>();
    List<SlogoNode> roots = new ArrayList<>();
    for(String s : cleaned){
      String symbol = parser.getSymbol(s);
      SlogoNode curr;
      switch(symbol){
        case "Constant" -> {
          curr = new ConstantNode(Double.parseDouble(s));
          // if stack is empty and we see a constant, it doesn't do anything to the program but
          // we still add it to the tree
        }
        case "Forward", "Backward", "Left", "Right" -> {
          curr = new TurtleNode(1, symbol);
        }
        case "Sum", "Difference" -> {
          curr = new MathNode(2, symbol);
        }
        case "ListStart", "ListEnd" -> {
          curr = new BracketNode(symbol);
        }
        case "If" -> {
          curr = new ConditionalNode(symbol);
        }
        default -> throw new IllegalArgumentException();
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
      System.out.println(root.getReturnValue(commands));
    }
  }

  private List<String> cleanInput(String input) {
    String[] preCleaned = input.split(NEWLINE);
    List<String> cleaned = new ArrayList<>();
    for(String line : preCleaned){
      if(!parser.getSymbol(line).equals("NO MATCH")){
        System.out.println(line);
      }
      if (!parser.getSymbol(line).equals("Comment")){
        System.out.println("comment");
        System.out.println(line);
        cleaned.addAll(Arrays.asList(line.split(WHITESPACE)));
      }
    }
    return cleaned;
  }
}
