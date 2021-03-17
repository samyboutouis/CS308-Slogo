package slogo.controller;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.model.CommandReader;

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

  public List<Command> parseProgram(String program, BackEndTurtle backEndTurtle) {
    return commandReader.parseInput(program, backEndTurtle);
  }

  public void setLanguage (String language) {
    this.language = translationBundle.getString(language);
    commandReader.setLanguage(this.language);
  }


  public Map<String, Double> getVariables() {
    return commandReader.getVariables();
  }
}
