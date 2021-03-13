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

    public void addNode(SlogoNode node) {
      parameters.add(node);
    }

    public boolean isFull() {
      return parameters.size() == numParameters;
    }

    protected List<SlogoNode> getParameters() {
      return parameters;
    }

    abstract double getReturnValue(List<Command> commands);
}

