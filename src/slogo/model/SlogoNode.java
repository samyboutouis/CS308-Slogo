package slogo.model;

import java.util.ArrayList;
import java.util.List;

public abstract class SlogoNode {

  private List<SlogoNode> parameters;
  private int numParameters;
  private String myString;
  private boolean BooleanNode = false;

  public SlogoNode(int numParameters) {
    this.numParameters = numParameters;
    parameters = new ArrayList<>();
  }

  public void addNode(SlogoNode node) {
    parameters.add(node);
  }

  public String getMyString() {
    StringBuilder ret = new StringBuilder(myString);
    for (SlogoNode s : parameters) {
      ret.append(" ").append(s.getMyString());
    }
    return ret.toString();
  }

  public void setString(String s) {
    myString = s;
  }

  public void setBooleanNode(boolean flag) {
    BooleanNode = flag;
  }

  public boolean getBooleanNode() {
    return BooleanNode;
  }

  public boolean isFull() {
    return parameters.size() == numParameters;
  }

  protected List<SlogoNode> getParameters() {
    return parameters;
  }

  public abstract double getReturnValue(BackEndTurtleTracker tracker);
}

