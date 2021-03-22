package slogo.controller;

import java.util.Map;
import java.util.ResourceBundle;
import slogo.SafeBackEndTurtleTracker;
import slogo.model.CommandReader;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.observers.XMLObserver;

public class Controller {

  private static final String INITIAL_LANGUAGE = "English";

  private String language;
  private final CommandReader commandReader;
  private final ResourceBundle languageBundle;
  private final ResourceBundle translationBundle;
  private XMLObserver xmlObserver;

  public Controller() {
    language = INITIAL_LANGUAGE;
    commandReader = new CommandReader(language);
    translationBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "languages", "TranslateOptions"));
    languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", "resources", "languages", "LanguageOptions"));
  }

  public SafeBackEndTurtleTracker parseProgram(String program,
      BackEndTurtleTracker backEndTurtleTracker) {
    return commandReader.parseInput(program, backEndTurtleTracker);
  }

  public void setLanguage(String language) {
    this.language = translationBundle.getString(language);
    commandReader.setLanguage(this.language);
  }

  public void setTranslatedLanguage(String language) {
    this.language = language;
    notifyXMLObserver(languageBundle.getString(language));
  }

  public void addXMLObserver(XMLObserver xmlObserver) {
    this.xmlObserver = xmlObserver;
  }

  public void notifyXMLObserver(String language) {
    xmlObserver.updateLanguage(language);
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
