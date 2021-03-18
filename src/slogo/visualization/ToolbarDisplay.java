package slogo.visualization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import slogo.controller.Controller;
import slogo.controller.FrontEndController;

public class ToolbarDisplay {
  private final static int PADDING_LENGTH = 10;
  private final static int COLUMN_COUNT = 10;
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String REFERENCES_FOLDER = "src/resources/reference";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String METHODS_PROPERTY = "reflection/ButtonMethods";

  private final GridPane gridPane;
  private final Controller controller;
  private final FrontEndController frontEndController;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;

  public ToolbarDisplay(GridPane pane, String resourcePackage, Controller controller, FrontEndController frontEndController) {
    String language = DEFAULT_LANGUAGE;
    this.gridPane = pane;
    this.controller = controller;
    this.languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + METHODS_PROPERTY);
    this.frontEndController = frontEndController;
    initializeGridPane();
    makeToolbar();
  }

  private void initializeGridPane() {
    gridPane.setHgap(PADDING_LENGTH);
    gridPane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));
    for (int i = 0; i < COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      gridPane.getColumnConstraints().add(col);
    }
    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    row.setPercentHeight(100.0);
    gridPane.getRowConstraints().add(row);
  }

  private void makeToolbar() {
    int colIndex = 0;
    ButtonFactory buttonFactory = new ButtonFactory(frontEndController);
    for(String label : commandBundle.keySet()) {
      gridPane.add(buttonFactory.createButton(label), colIndex++, 0, 1, 1);
    }
    addLanguageDropdown();
    addHelpDropdown();
  }

  private void addLanguageDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(idBundle.getString("LanguageDropdown"));
    for (String language : languageBundle.keySet()) {
      comboBox.getItems().add(languageBundle.getString(language));
    }
    comboBox.setValue(DEFAULT_LANGUAGE);
    comboBox.setOnAction(event -> handleLanguageClick(comboBox.getValue()));
    gridPane.add(comboBox, 4, 0, 4, 1);
  }

  private void addHelpDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(idBundle.getString("HelpDropdown"));
    File folder = new File(REFERENCES_FOLDER);
    File[] files = folder.listFiles();
    List<String> commands = new ArrayList<>();
    for (File file : files) {
      commands.add(file.getName());
    }
    Collections.sort(commands);
    comboBox.getItems().addAll(commands);
    comboBox.setValue(resourceBundle.getString("HelpButton"));
    comboBox.setOnAction(event -> handleHelpClick(comboBox.getValue()));
    gridPane.add(comboBox, 9, 0, 2, 1);
  }

  private void handleLanguageClick(String language) {
    controller.setLanguage(language);
  }

  private void handleHelpClick(String command) {
    try {
      String text = new String(Files.readAllBytes(Paths.get(REFERENCES_FOLDER + "/" + command)));
      Alert alert = new Alert(AlertType.INFORMATION, text);
      alert.setHeaderText(command);
      alert.showAndWait();
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR, resourceBundle.getString("HelpError"));
      alert.showAndWait();
    }
  }

  public GridPane getGridPane() {
    return gridPane;
  }
}
