package slogo.model;

public class mainTester {
  public static void main(String[] args){
    CommandReader t = new CommandReader("English");
    t.parseInput("# hello");
  }
}
