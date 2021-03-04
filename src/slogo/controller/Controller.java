package slogo.controller;

import java.util.Arrays;
import java.util.List;
import slogo.model.ProgramParser;

public class Controller {
  // regular expression representing one or more whitespace characters (space, tab, or newline)
  public static final String WHITESPACE = "\\s+";
  private String language;

  public Controller() {
    language = "English";
    parseProgram("fd 50 rt 90 BACK :distance Left :angle");
  }

  public void parseProgram(String program) {
    ProgramParser parser = new ProgramParser();
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText(parser, Arrays.asList(program.split(WHITESPACE)));
  }

  // given some text, prints results of parsing it using the given language
  private void parseText (ProgramParser lang, List<String> lines) {
    for (String line : lines) {
      if (line.trim().length() > 0) {
        System.out.println(String.format("%s : %s", line, lang.getSymbol(line)));
      }
    }
    System.out.println();
  }
}
