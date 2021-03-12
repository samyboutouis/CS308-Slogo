package slogo.model;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;

public abstract class SlogoNode {
    private List<SlogoNode> parameters;
    private String type;
    private int numParameters;

    public SlogoNode(int numParameters) {
      this.numParameters = numParameters;
      parameters = new ArrayList<>();
    }

    public SlogoNode(int numParameters, String type) {
     this(numParameters);
     this.type = type;
    }

    public void addNode(SlogoNode node) {
      parameters.add(node);
    }

    public boolean isFull() {
      return parameters.size() == numParameters;
    }

    protected void setType(String type) {
      this.type = type;
    }

    protected String getType() {
      return type;
    }

    protected List<SlogoNode> getParameters() {
      return parameters;
    }

    protected abstract double getReturnValue(List<Command> commands);
}

