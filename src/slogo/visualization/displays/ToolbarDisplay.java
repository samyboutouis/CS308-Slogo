package slogo.visualization.displays;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import slogo.controller.Controller;
import slogo.visualization.FrontEndController;
import slogo.visualization.ButtonFactory;
import slogo.visualization.turtle.FrontEndTurtleTracker;
import slogo.visualization.observers.XMLObserver;

public class ToolbarDisplay implements XMLObserver {
  private static final String COLOR_PICKER_ID = "ColorPicker";
  private static final int PADDING_LENGTH = 10;
  private static final int COLUMN_COUNT = 10;
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String REFERENCES_FOLDER = "src/resources/reference";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String DISPLAY_CLASS_NAME = "displayWindow";
  private static final Color DEFAULT_COLOR = Color.web("#dedcdc");
  private static final String BACKGROUND_COLOR_LABEL = "Background: ";

  private final GridPane gridPane;
  private final Controller controller;
  private final FrontEndController frontEndController;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final List<String> toggleButtonList;
  private final List<String> defaultButtonList;
  private final FrontEndTurtleTracker turtleTracker;
  private Color backgroundColor;
  private ComboBox<String> languageBox;

  public ToolbarDisplay(String resourcePackage, Controller controller,
      FrontEndController frontEndController, FrontEndTurtleTracker frontEndTurtleTracker) {
    String language = DEFAULT_LANGUAGE;
    this.gridPane = new GridPane();
    this.controller = controller;
    this.languageBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    this.resourceBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.frontEndController = frontEndController;
    this.toggleButtonList = List.of("AddTurtleButton", "ColorThemeButton");
    this.defaultButtonList = List.of("NewWorkspaceButton", "LoadWorkspaceButton", "SaveWorkspaceButton");
    this.backgroundColor = DEFAULT_COLOR;
    this.turtleTracker = frontEndTurtleTracker;
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
    gridPane.getStyleClass().add(DISPLAY_CLASS_NAME);
  }

  private void makeToolbar() {
    controller.addXMLObserver(this);
    gridPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    int colIndex = 0;
    ButtonFactory buttonFactory = new ButtonFactory(frontEndController);
    for (String label : toggleButtonList) {
      gridPane.add(buttonFactory.createToggleButton(label), colIndex++, 0, 1, 1);
    }
    addBackgroundColorPicker(colIndex++);
    for (String label : defaultButtonList) {
      gridPane.add(buttonFactory.createDefaultButton(label), colIndex++, 0, 1, 1);
    }
    addLanguageDropdown(colIndex++);
    colIndex++;
    addHelpDropdown(colIndex);
  }

  private void addBackgroundColorPicker(int colIndex) {
    HBox hBox = new HBox();
    ColorPicker colorPicker = new ColorPicker(backgroundColor);
    colorPicker.setId(COLOR_PICKER_ID);
    colorPicker
        .setOnAction(event -> handleBackgroundColorPicker(colorPicker));
    hBox.getChildren().addAll(new Label(BACKGROUND_COLOR_LABEL), colorPicker);
    hBox.setAlignment(Pos.CENTER);
    gridPane.add(hBox, colIndex, 0, 1, 1);
  }

  private void addLanguageDropdown(int colIndex) {
    languageBox = new ComboBox<>();
    languageBox.setId(idBundle.getString("LanguageDropdown"));
    for (String language : languageBundle.keySet()) {
      languageBox.getItems().add(languageBundle.getString(language));
    }
    languageBox.setValue(DEFAULT_LANGUAGE);
    languageBox.setOnAction(event -> handleLanguageClick(languageBox.getValue()));
    gridPane.add(languageBox, colIndex, 0, 4, 1);
  }

  private void addHelpDropdown(int colIndex) {
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
    gridPane.add(comboBox, colIndex, 0, 2, 1);
  }

  private void handleBackgroundColorPicker(ColorPicker colorPicker) {
    backgroundColor = colorPicker.getValue();
    turtleTracker.notifyBackgroundObservers(backgroundColor);
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

  public GridPane getPane() {
    return gridPane;
  }

  public void updateLanguage(String language) {
    languageBox.setValue(language);
  }
}
