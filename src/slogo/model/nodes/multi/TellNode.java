package slogo.model.nodes.multi;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.ListEndNode;

public class TellNode extends SlogoNode {
  private List<SlogoNode> parameters;
  private int brackets;

  public TellNode(int parameter){
    super(parameter);
    brackets = parameter;
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



  @Override
  public double getReturnValue(TurtleTracker tracker) {
    int ret=0;
    for (int i=1; i<parameters.size()-1;i++){
      ret= (int) parameters.get(i).getReturnValue(tracker); // should all be constant nodes
      tracker.addTurtle(new BackEndTurtle(0,0,0,true,true, true, 0));
    }
    return ret;
  }

}
