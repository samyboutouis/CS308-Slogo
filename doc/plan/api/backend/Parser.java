package slogo.backend;

public interface Parser {
  public MovementList parseCommand(String input, Movement turtleInfo) throws IllegalFormatException;
  // will utilize backend internal methods to break the string into commands, then each command into a movement, and then combine each movement into a MovementList

  public void setLanguage(String language);
  // sets the language of the commands that the parser will use to evaluate what the user wants to do
}
