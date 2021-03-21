package slogo.controller;

import java.util.Map;
import java.util.ResourceBundle;
import slogo.BackEndTurtle;
import slogo.SafeBackEndTurtleTracker;
import slogo.model.CommandReader;
import slogo.model.BackEndTurtleTracker;

public class Controller {

  private static final String INITIAL_LANGUAGE = "English";

  private String language;
  private final CommandReader commandReader;
  private final ResourceBundle translationBundle;

  public Controller() {
    language = INITIAL_LANGUAGE;
    commandReader = new CommandReader(language);
    translationBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "languages", "TranslateOptions"));
  }

  public SafeBackEndTurtleTracker parseProgram(String program,
      BackEndTurtleTracker backEndTurtleTracker) {
    return commandReader.parseInput(program, backEndTurtleTracker);
  }

  public void setLanguage(String language) {
    this.language = translationBundle.getString(language);
    commandReader.setLanguage(this.language);
  }


  public Map<String, Double> getVariables() {
    return commandReader.getVariables();
  }

  public Map<String, String> getUserDefinedCommands() {
    return commandReader.getUserDefinedCommandsInString();
  }

  public String getLanguage() {
    return language;
  }
}
