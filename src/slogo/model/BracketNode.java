package slogo.model;

import java.util.List;
import slogo.Command;

public class BracketNode extends SlogoNode{

  public BracketNode(String type) {
    super(0, type);
  }

  @Override
  public boolean isFull() {
    return true;
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    // nodes that use bracket node will know to ignore the bracket return value
    return 0;
  }
}
