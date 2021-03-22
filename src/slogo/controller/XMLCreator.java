package slogo.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.FrontEndTurtleTracker;
import slogo.SafeTurtle;
import slogo.visualization.Workspace;

public class XMLCreator {
  private static final String RESOURCES_FILE = "resources.languages.EnglishErrors";
  private static final String DEFAULT_FILE_PATH = "src/resources/preferences/";
  private static final String XML = ".xml";

  private final FrontEndTurtleTracker turtleTracker;
  private final Controller controller;
  private final Workspace workspace;
  private Document doc;
  private String filePath;
  private ResourceBundle errorBundle;

  public XMLCreator(FrontEndTurtleTracker frontEndTurtleTracker, Controller controller, Workspace workspace,
    String filePath) {
    this.filePath = filePath;
    this.errorBundle = ResourceBundle.getBundle(RESOURCES_FILE);
    this.turtleTracker = frontEndTurtleTracker;
    this.controller = controller;
    this.workspace = workspace;
    try {
      initialize();
      makeDocument();
      makeFile();
      showCreationSuccess();
    } catch (Exception e) {
      showCreationError();
    }
  }

  private void initialize() throws Exception {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.newDocument();
    } catch (Exception e) {
      throw new Exception();
    }
  }

  private void makeDocument() {
    Element root = doc.createElement("Workspace");
    doc.appendChild(root);
    recordTurtles(root);
    recordPreferences(root);
  }

  private void addToDocument(Element root, String tag, String value) {
    Element element = doc.createElement(tag);
    element.appendChild(doc.createTextNode(value));
    root.appendChild(element);
  }

  private void recordTurtles(Element root) {
    Element turtlesTag = doc.createElement("Turtles");
    List<Integer> allTurtleIDs = turtleTracker.getTurtleIDs();
    for(int id : allTurtleIDs) {
      Element turtleTag = doc.createElement("Turtle");
      SafeTurtle turtle = turtleTracker.getSafeTurtle(id);
      turtleTag.appendChild(createLineTag(turtle.getLineInfo()));
      addToDocument(turtleTag, "XPosition", String.valueOf(turtle.getX()));
      addToDocument(turtleTag, "YPosition", String.valueOf(turtle.getY()));
      addToDocument(turtleTag, "Direction", String.valueOf(turtle.getDirection()));
      addToDocument(turtleTag, "PenColor", String.valueOf(turtle.getPenColor()));
      addToDocument(turtleTag, "PenThickness", String.valueOf(turtle.getPenThickness()));
      addToDocument(turtleTag, "Active", String.valueOf(turtle.isActive()));
      addToDocument(turtleTag, "PenDown", String.valueOf(turtle.isPenDown()));
      addToDocument(turtleTag, "Showing", String.valueOf(turtle.isShowing()));
      turtlesTag.appendChild(turtleTag);
    }
    root.appendChild(turtlesTag);
  }

  private Element createLineTag(List<Map<String, String>> lineList) {
    Element linesTag = doc.createElement("Lines");
    for(Map<String, String> lineInfo : lineList){
      Element lineTag = doc.createElement("Line");
      for(String key : lineInfo.keySet()) {
        addToDocument(lineTag, key, lineInfo.get(key));
      }
      linesTag.appendChild(lineTag);
    }
    return linesTag;
  }

  private void recordPreferences(Element root) {
    addToDocument(root, "Language", controller.getLanguage());
    addToDocument(root, "BackgroundColor", String.valueOf(turtleTracker.getBackgroundColor()));
    addToDocument(root, "ColorTheme", workspace.getStylesheet());
  }

  private void makeFile() {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource domSource = new DOMSource(doc);
      StreamResult streamResult = new StreamResult(new File(DEFAULT_FILE_PATH + filePath + XML));
      transformer.transform(domSource, streamResult);
    } catch (TransformerException e) {
      showCreationError();
    }
  }

  private void showCreationError() {
    AlertType type = AlertType.ERROR;
    String message = errorBundle.getString("creation_error");
    new Alert(type, message).showAndWait();
  }

  private void showCreationSuccess() {
    AlertType type = AlertType.CONFIRMATION;
    new Alert(type, DEFAULT_FILE_PATH + filePath + XML).showAndWait();
  }
}
