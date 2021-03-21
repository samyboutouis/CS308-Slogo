package slogo.controller;

import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLParser {
  private static final String RESOURCES_FILE = "resources.languages.EnglishErrors";

  private Document doc;
  private ResourceBundle resourceBundle;

  public XMLParser(File file) throws Exception {
    resourceBundle = ResourceBundle.getBundle(RESOURCES_FILE);
    try {
      initialize(file);
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

  private void addToMap(String key, Map<String, String> map, boolean optional) throws Exception {
    NodeList nodes = doc.getElementsByTagName(key);
    if (nodes.getLength() == 0 && !optional) {
      throw new Exception();
    } else if (optional && nodes.getLength() == 0) {
      map.put(key, "");
    } else {
      map.put(key, nodes.item(0).getTextContent());
    }
  }
}
