package slogo.controller;

import java.io.File;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLParser {

  private Document doc;
  private String language;
  private Color backgroundColor;
  private String stylesheet;

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
  }

  private String findValue(String key) throws Exception {
    NodeList nodes = doc.getElementsByTagName(key);
    if (nodes.getLength() == 0) {
      throw new Exception();
    } else {
      return nodes.item(0).getTextContent();
    }
  }

  public String getLanguage() {
    return language;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public String getStylesheet() {
    return stylesheet;
  }
}
