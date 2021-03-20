package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.ListEndNode;

public class AskNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private int brackets;
  private int firstEnd;

  public AskNode(int numParameters) {
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
  private List<Integer> getAskTurtles(BackEndTurtleTracker tracker) {
    List<Integer> ret = new ArrayList<>();
    for(int i = 1; i < firstEnd; i++) {
      try{
        int turtleId = (int) ((ConstantNode) parameters.get(i)).getReturnValue(tracker);
        // should all be constant nodes
        ret.add(turtleId);
      } catch (ClassCastException e) {
        System.out.println("Ask Turtle Format Incorrect!");
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
