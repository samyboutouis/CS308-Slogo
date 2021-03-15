package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

// second level of abstraction for all command nodes that may need getValues
public abstract class TurtleCommandNode extends SlogoNode {

  public TurtleCommandNode(int numParameters) {
    super(numParameters);
  }

  // for subclasses to get their parameter values
  // gets values for all parameters of node that calls this method, needs commands list to
  // create the commands when the parameters call getReturnValue
  protected List<Double> getValues(List<Command> commands, List<SlogoNode> parameters) {
    List<Double> values = new ArrayList<>();
    for(SlogoNode node : parameters) {
      values.add(node.getReturnValue(commands));
    }
    return values;
  }
}
