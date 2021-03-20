package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.ListEndNode;

public class AskWithNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private int brackets;
  private int firstEnd;

  public AskWithNode(int numParameters) {
    super(numParameters);
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull(){
    return !parameters.isEmpty() && checkBrackets();
  }

  private boolean checkBrackets() {
    int seen = 0;
    for(SlogoNode node : parameters){
      if(node instanceof ListEndNode){
        seen++;
      }
    }
    return seen == brackets;
  }

  private void setFirstEnd() {
    for(int i = 0; i < parameters.size(); i++){
      if(parameters.get(i) instanceof ListEndNode){
        firstEnd = i;
        break;
      }
    }
  }

  // gets all the turtles that the ask command wants
  // can't use existing lambdas since we want to ONLY have one active turtle when checking the condition
  // tellList should hold values already so we can wipe out active list each time
  private List<Integer> getAskTurtles(BackEndTurtleTracker tracker) {
    List<Integer> ret = new ArrayList<>();
    try{
    assert parameters.get(1).getBooleanNode();}
    catch (ClassCastException e){
      System.out.println("Not a Boolean Expression in AskWith");
    }
    for (Integer i: tracker.getAllTurtles()){
      tracker.checkOneTurtle(i);
      if (parameters.get(1).getReturnValue(tracker)!=0){
        ret.add(i);
      }
    }

    return ret;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    setFirstEnd();
    List<Integer> askTurtleList = getAskTurtles(tracker);
    tracker.setAskList(askTurtleList);
    // create AskCommand that sets turtles active

    double ret = 0;
    for(int i = firstEnd + 2; i < parameters.size() - 1; i++) {
      ret = parameters.get(i).getReturnValue(tracker);
    }

    // create TellCommand that sets old values to be active
    tracker.revertAskList();
    return ret;
  }
}

