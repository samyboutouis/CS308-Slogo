package slogo.model;

import java.util.List;
import slogo.Command;
import slogo.model.nodes.control.VariableNode;

// implements repeat, dotimes, for
public class LoopNode extends SlogoNode{
  private List<SlogoNode> parameters;
  private VariableNode variable;

  public LoopNode(String type) {
    super(0); // parameters being full determined by bracket, just like conditional node
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    // not done
    // return !parameters.isEmpty() && parameters.get(parameters.size() - 1).getType().equals("ListEnd");
    return true;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    double ret = 0;
    int start = 0;
    int end = 0;
//    switch(super.getType()){
//      case "Repeat" -> {
//        // TO DO
//        end = (int) parameters.get(0).getReturnValue(commands);
//      }
//    }
//    for(int i = start; i < end; i++){ // how many times our loop loops
//      variable.setValue(i);
//      for(int j = 1; j < parameters.size(); j++){ // runs through all the commands in the loop
//        ret = parameters.get(j).getReturnValue(commands);
//      }
//    }
    return ret;
  }

  private void getStartAndEnd() {
    // switch for type of loop
    //
//    switch(super.getType()){
//      case "For" -> {
//        // knows there are two brackets...
//      }
//    }
  }
}
