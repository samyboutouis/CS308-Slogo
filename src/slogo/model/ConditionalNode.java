package slogo.model;

import java.util.List;
import slogo.Command;

public class ConditionalNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public ConditionalNode(String type) {
    super(0, type); // dummy value for parameters
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1).getType().equals("ListEnd");
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    // assume expr is just one tree
    switch(super.getType()){
      case "If" -> {
        double ret = 0;
        if(parameters.get(0).getReturnValue(commands) != 0.0) {
          for(int i = 1; i < parameters.size(); i++){
            if(!parameters.get(i).getType().equals("ListStart") && !parameters.get(i).getType().equals("ListEnd")){
              ret = parameters.get(i).getReturnValue(commands);
            }
          }
        }
        return ret; // ret = 0 if expr is 0
      }
    }
    return 0;
  }
}
