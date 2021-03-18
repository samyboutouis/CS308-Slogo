package slogo.model;

import java.util.ArrayList;
import java.util.List;

public abstract class SlogoNode {
    private List<SlogoNode> parameters;
    private int numParameters;
    private String myString;

    public SlogoNode(int numParameters) {
      this.numParameters = numParameters;
      parameters = new ArrayList<>();
    }

    public void addNode(SlogoNode node) {
      parameters.add(node);
    }

    public String getMyString(){
      String ret = myString;
      for (SlogoNode s : parameters){
        ret = ret + " " + s.getMyString();
      }
      return ret;
      }

    public void setString(String s){
      myString = s;
    }

    public boolean isFull() {
      return parameters.size() == numParameters;
    }

    protected List<SlogoNode> getParameters() {
      return parameters;
    }

    public abstract double getReturnValue(TurtleTracker tracker);
}

