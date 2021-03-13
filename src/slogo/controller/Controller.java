package slogo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import slogo.model.ProgramParser;

public class Controller {
  // regular expression representing one or more whitespace characters (space, tab, or newline)
  public static final String WHITESPACE = "\\s+";
  private static final String INITIAL_LANGUAGE = "English";

  private String language;
  private final ProgramParser parser;
  private final ResourceBundle translationBundle;

  public Controller() {
    language = INITIAL_LANGUAGE;
    parser = new ProgramParser();
    translationBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", "resources", "languages", "TranslateOptions"));
    //parseProgram("fd 50 rt 90 BACK :distance Left :angle");
  }

  public void parseProgram(String program) {
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText(parser, Arrays.asList(program.split(WHITESPACE)));
  }

  // given some text, prints results of parsing it using the given language
  private void parseText (ProgramParser lang, List<String> lines) {
    for (String line : lines) {
      if (line.trim().length() > 0) {
        //System.out.printf("%s : %s%n", line, lang.getSymbol(line));
      }
    }
    System.out.println();
  }

  public void setLanguage (String language) {
    this.language = translationBundle.getString(language);
    parser.addPatterns(this.language);
  }
}
