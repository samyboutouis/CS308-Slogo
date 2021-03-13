package slogo.visualization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.controller.Controller;

public class ToolbarDisplay {
  private final static int PADDING_LENGTH = 10;
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private final static int COLUMN_COUNT = 10;
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String REFERENCES_FOLDER = "src/resources/reference";
  private static final String IMAGE_PROPERTY = "stylesheets/Image";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String COMMAND_PROPERTY = "commands/Commands";

  private final Stage stage;
  private final GridPane gridPane;
  private final TurtleDisplay turtleDisplay;
  private final Controller controller;
  private final String resourcePackage;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle imageBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;
  private Color backgroundColor;
  private Color penColor;
  private List<String> buttonList;

  public ToolbarDisplay(GridPane pane, String resourcePackage, Stage stage,
    TurtleDisplay turtleDisplay, Controller controller) {
    String language = "English";
    this.gridPane = pane;
    this.stage = stage;
    this.turtleDisplay = turtleDisplay;
    this.controller = controller;
    this.resourcePackage = resourcePackage;
    this.languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.imageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + IMAGE_PROPERTY);
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + COMMAND_PROPERTY);
    backgroundColor = Color.web("#dedcdc");
    penColor = Color.BLACK;
    buttonList = List.of("PenColorButton", "BackgroundColorButton", "TurtleImageButton");
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
    for(String label : buttonList) {
      gridPane.add(makeButton(label), colIndex++, 0, 1, 1);
    }
    addLanguageDropdown();
    addHelpDropdown();
  }

  private Button makeButton(String property) {
    Button button = new Button();
    button.setId(idBundle.getString(property));
    String label = imageBundle.getString(property);
    System.out.println(label);
    button.setGraphic(new ImageView(
      new Image(DEFAULT_RESOURCE_FOLDER + label, ICON_WIDTH,
        ICON_HEIGHT, false, false)));
    button.setOnAction(handler -> {
      try {
        Method m = this.getClass()
          .getDeclaredMethod(commandBundle.getString(property), Button.class);
        m.invoke(this, button);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return button;
  }

  private void addLanguageDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(idBundle.getString("LanguageDropdown"));
    for (String language : languageBundle.keySet()) {
      comboBox.getItems().add(languageBundle.getString(language));
    }
    comboBox.setValue("English");
    comboBox.setOnAction(event -> handleLanguageClick(comboBox.getValue()));
    gridPane.add(comboBox, 3, 0, 4, 1);
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

  private void handlePenColorClick(Button penColorButton) {
    penColorButton.setVisible(false);
    penColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(penColor);
    colorPicker.setOnAction(event -> handlePenColorPicker(penColorButton, colorPicker));
    gridPane.add(colorPicker, 0, 0, 1, 1);
  }

  private void handlePenColorPicker(Button penColorButton, ColorPicker colorPicker) {
    penColor = colorPicker.getValue();
    turtleDisplay.setPenColor(penColor);
    gridPane.getChildren().remove(colorPicker);
    penColorButton.setVisible(true);
    penColorButton.setDisable(false);
  }

  private void handleBackgroundColorClick(Button backgroundColorButton) {
    backgroundColorButton.setVisible(false);
    backgroundColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(backgroundColor);
    colorPicker
      .setOnAction(event -> handleBackgroundColorPicker(backgroundColorButton, colorPicker));
    gridPane.add(colorPicker, 1, 0, 1, 1);
  }

  private void handleBackgroundColorPicker(Button backgroundColorButton, ColorPicker colorPicker) {
    backgroundColor = colorPicker.getValue();
    turtleDisplay.setBackgroundColor(backgroundColor);
    gridPane.getChildren().remove(colorPicker);
    backgroundColorButton.setVisible(true);
    backgroundColorButton.setDisable(false);
  }

  private void handleTurtleImageClick(Button button) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Image File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters()
      .setAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.svg"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtleDisplay.setTurtleImage(file);
    }
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
}
