package slogo.model;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;

public abstract class SlogoNode {
    private List<SlogoNode> parameters;
    private int numParameters;

    public SlogoNode(int numParameters) {
      this.numParameters = numParameters;
      parameters = new ArrayList<>();
    }

    protected abstract double getReturnValue(List<Command> commands);

    protected abstract void createMovement();
  }
}
