package slogo.model;

import java.util.List;
import slogo.Command;

public class ConstantNode extends SlogoNode{
  private double value;

  public ConstantNode(double value) {
    super(0);
    this.value = value;
  }

  @Override
  protected double getReturnValue(List<Command> commands) {
    return value;
  }

  @Override
  protected void createMovement() {

  }
}
