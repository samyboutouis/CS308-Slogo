package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;

// what is created when a group start "(" is seen, represents the entire grouping structure
public class GroupStartNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private SlogoNode command;
  private List<Double> values;
  private int arguments;

  public GroupStartNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
    values = new ArrayList<>();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof GroupEndNode;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    command = parameters.get(0);
    getArguments(tracker);
    double ret = command.getReturnValue(tracker);
    for(int i = 0; i < values.size(); i = i + arguments){
      // i represents start of subList of values
      List<SlogoNode> newParams = getNewParams(i, i + arguments);
      command.replaceParameters(newParams);
      ret = ret + command.getReturnValue(tracker);
      // call command again with new parameters
    }
    return ret;
  }

  // ( sum 50 49 48 47 46 45 )
  // i = 0 is command node, i = 1 and on is extra values (since command node takes in its original parameters)
  // values: 48 47 46 45

  // plan to just sum up the output values, since sum grouping is very different than palette grouping
  // grouping is equivalent to calling the same command multiple times, but I assume the outputs are summed up

  private List<SlogoNode> getNewParams(int start, int end) {
    List<SlogoNode> ret = new ArrayList<>();
    for(int i = start; i < end; i++){
      ret.add(new ConstantNode(0, values.get(i)));
      // to be sent to command again
    }
    return ret;
  }

  private void getArguments(BackEndTurtleTracker tracker) {
    arguments = command.getNumParameters();
    if( (parameters.size() - 2) % arguments != 0) {
      // subtract off command node and group end
      throw new IllegalArgumentException("Grouping does not have correct multiple of arguments!");
    }
    // get each value in the group, -1 for avoiding group end
    for(int i = 1; i < parameters.size() - 1; i++) {
      values.add(parameters.get(i).getReturnValue(tracker));
    }
  }

}
