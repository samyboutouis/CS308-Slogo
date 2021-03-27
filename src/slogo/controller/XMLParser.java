package slogo.controller;

import java.io.File;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Class that reads in an XML preference file and returns the necessary variables to update a
 * workspace state with the appropriate values. Requires a file to be passed in but handles
 * exceptions if the file is ill-formatted or incorrect.
 *
 * @author Samy Boutouis
 */
public class XMLParser {

  private Document doc;
  private String language;
  private Color backgroundColor;
  private String stylesheet;
  private int numTurtles;

  /**
   * Constructor for class.
   *
   * @param file File object that stores the XML file
   * @throws Exception Exception that leads to error being displayed for invalid file being uploaded
   */
  public XMLParser(File file) throws Exception {
    try {
      initialize(file);
      parseFile();
    } catch (Exception e) {
      throw new Exception();
    }
  }

  private void initialize(File file) throws Exception {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(file);
      doc.getDocumentElement().normalize();
    } catch (Exception e) {
      throw new Exception();
    }
  }

  private void parseFile() throws Exception {
    language = findValue("Language");
    backgroundColor = Color.web(findValue("BackgroundColor"));
    stylesheet = findValue("ColorTheme");
    numTurtles = findCount("Turtle");
  }

  private String findValue(String key) throws Exception {
    NodeList nodes = doc.getElementsByTagName(key);
    if (nodes.getLength() == 0) {
      throw new Exception();
    } else {
      return nodes.item(0).getTextContent();
    }
  }

  private int findCount(String key) throws Exception {
    NodeList nodes = doc.getElementsByTagName(key);
    if (nodes.getLength() == 0) {
      throw new Exception();
    } else {
      return nodes.getLength();
    }
  }

  /**
   * Gets the language indicated in the XML file
   *
   * @return String indicating language of the workspace
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Gets the background color indicated in the XML file
   *
   * @return Color indicating the background color of the turtle display
   */
  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Gets the color theme/stylesheet of the workspace
   *
   * @return String of the stylesheet file of the workspace
   */
  public String getStylesheet() {
    return stylesheet;
  }

  /**
   * Gets the number of turtles on the screen
   *
   * @return Integer representing number of turtles on the screen
   */
  public int getNumTurtles() {
    return numTurtles;
  }
}
