package slogo.model.nodes;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class ListEndNode extends SlogoNode {

  public ListEndNode(int numParameters) {
    super(numParameters); // numParameters = 0, so it is always full
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    // nodes that use bracket node will know to ignore the bracket return value
    return 0;
  }
}