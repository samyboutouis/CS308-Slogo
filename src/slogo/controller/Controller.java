package slogo.controller;

import java.util.Map;
import java.util.ResourceBundle;
import slogo.SafeBackEndTurtleTracker;
import slogo.model.CommandReader;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.observers.XMLObserver;

/**
 * Controller class that enables communication between the front and back-ends to parse code written
 * by the user. In charge of managing the state of the command reader and how it receives input from
 * the front-end. Also in charge of maintaining the language of the workspace.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 */
public class Controller {

  private static final String INITIAL_LANGUAGE = "English";

  private String language;
  private final CommandReader commandReader;
  private final ResourceBundle languageBundle;
  private final ResourceBundle translationBundle;
  private XMLObserver xmlObserver;

  /**
   * Constructor for the class.
   */
  public Controller() {
    language = INITIAL_LANGUAGE;
    commandReader = new CommandReader(language);
    translationBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "languages", "TranslateOptions"));
    languageBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "languages", "LanguageOptions"));
  }

  /**
   * Parses the user's program and returns an object that allows the front-end to read in commands
   *
   * @param program String that is the code the user wrote
   * @param backEndTurtleTracker BackEndTurtleTracker with turtle information
   * @return Interface that allows limited access to the turtle tracker
   */
  public SafeBackEndTurtleTracker parseProgram(String program,
      BackEndTurtleTracker backEndTurtleTracker) {
    return commandReader.parseInput(program, backEndTurtleTracker);
  }

  /**
   * Sets the language of the parser.
   *
   * @param language String representing the language
   */
  public void setLanguage(String language) {
    this.language = translationBundle.getString(language);
    commandReader.setLanguage(this.language);
  }

  /**
   * Sets the language of other elements in the workspace when a new language is specified by loading
   * a preferences XML file.
   *
   * @param language String representing the language.
   */
  public void setTranslatedLanguage(String language) {
    this.language = language;
    notifyXMLObserver(languageBundle.getString(language));
  }

  /**
   * Add an XML Observer that updates when new XML preference files are loaded in.
   *
   * @param xmlObserver Class that implements the XMLObserver interface
   */
  public void addXMLObserver(XMLObserver xmlObserver) {
    this.xmlObserver = xmlObserver;
  }

  /**
   * Notifies observers that the language has changed due to loading in an XML file
   *
   * @param language String representing the language
   */
  public void notifyXMLObserver(String language) {
    xmlObserver.updateLanguage(language);
  }

  /**
   * Gets a map with all the variables and the values associated with it
   *
   * @return Map with all variables from the back end
   */
  public Map<String, Double> getVariables() {
    return commandReader.getVariables();
  }

  /**
   * Gets a map with all the user-defined functions and the code associated with it
   *
   * @return Map with all user defined-commands from the back end
   */
  public Map<String, String> getUserDefinedCommands() {
    return commandReader.getUserDefinedCommandsInString();
  }

  /**
   * Gets the language the parser is currently reading in.
   *
   * @return String representing the language
   */
  public String getLanguage() {
    return language;
  }
}
