package slogo.controller;

import java.io.File;
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

public class XMLCreator {

  private static final String RESOURCES_FILE = "resources.languages.EnglishErrors";

  private Document doc;
  private String filePath;
  private ResourceBundle errorBundle;

  public XMLCreator(String filePath) {
    this.filePath = filePath;
    this.errorBundle = ResourceBundle.getBundle(RESOURCES_FILE);
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
    Element root = doc.createElement("config");
    doc.appendChild(root);
//    Map<String, String> simulationInfo = configReader.getSimulationInfo();
//    for (String element : simulationInfo.keySet()) {
//      addToDocument(root, element, simulationInfo.get(element));
//    }
//    Map<String, String> cellConfigMap = configReader.getCellConfig();
//    Element cellConfigTag = doc.createElement("cellConfig");
//    root.appendChild(cellConfigTag);
//    for (String element : cellConfigMap.keySet()) {
//      addToDocument(cellConfigTag, element, cellConfigMap.get(element));
//    }
//    recordCells(root);
  }

  private void addToDocument(Element root, String tag, String value) {
    Element element = doc.createElement(tag);
    element.appendChild(doc.createTextNode(value));
    root.appendChild(element);
  }

//  private void recordCells(Element root) {
//    Element cellsTag = doc.createElement("cells");
//    root.appendChild(cellsTag);
//    int[][] stateGrid = grid.getStateIDGrid();
//    for (int[] ints : stateGrid) {
//      StringBuilder sb = new StringBuilder();
//      for (int col = 0; col < stateGrid[0].length; col++) {
//        sb.append(ints[col]);
//      }
//      Element rowTag = doc.createElement("row");
//      rowTag.appendChild(doc.createTextNode(sb.toString()));
//      cellsTag.appendChild(rowTag);
//    }
//  }

  private void makeFile() {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource domSource = new DOMSource(doc);
      StreamResult streamResult = new StreamResult(new File(filePath));
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
    new Alert(type, filePath).showAndWait();
  }
}
