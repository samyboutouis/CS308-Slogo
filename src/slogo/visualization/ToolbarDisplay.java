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

public class ToolbarDisplay {

  //FIXME: Button factory/creation design pattern

  private final static int PADDING_LENGTH = 10;
  private static final String PAINT_BRUSH_IMAGE = "resources/paint-brush.png";
  private static final String PAINT_BUCKET_IMAGE = "resources/paint-bucket.png";
  private static final String TURTLE_ICON_IMAGE = "resources/turtle-icon.png";
  private static final String REFERENCES_PATH = "src/resources/reference";
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private final static int COLUMN_COUNT = 10;

  private final Stage stage;
  private final GridPane gridPane;
  private final TurtleDisplay turtleDisplay;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private Button backgroundColorButton;
  private Button penColorButton;
  private Color backgroundColor;
  private Color penColor;

  public ToolbarDisplay(GridPane pane, String resourcePackage, Stage stage,
    TurtleDisplay turtleDisplay) {
    this.gridPane = pane;
    this.stage = stage;
    this.turtleDisplay = turtleDisplay;
    this.languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    String language = "English";
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "stylesheets", "CSS_IDs"));
    backgroundColor = Color.web("#dedcdc");
    penColor = Color.BLACK;
    initializeGridPane();
    setScreen();
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

  private void setScreen() {
    addPenColorButton();
    addBackgroundColorButton();
    addTurtleImageButton();
    addLanguageDropdown();
    addHelpDropdown();
  }

  private void addPenColorButton() {
    penColorButton = new Button();
    penColorButton.setOnAction(event -> handlePenColorClick());
    penColorButton.setGraphic(createImageView(PAINT_BRUSH_IMAGE));
    penColorButton.setId(idBundle.getString("PenColorButton"));
    gridPane.add(penColorButton, 0, 0, 1, 1);
  }

  private void addBackgroundColorButton() {
    backgroundColorButton = new Button();
    backgroundColorButton.setId(idBundle.getString("BackgroundColorButton"));
    backgroundColorButton.setOnAction(event -> handleBackgroundColorClick());
    backgroundColorButton.setGraphic(createImageView(PAINT_BUCKET_IMAGE));
    gridPane.add(backgroundColorButton, 1, 0, 1, 1);
  }

  private void addTurtleImageButton() {
    Button button = new Button();
    button.setId(idBundle.getString("TurtleImageButton"));
    button.setOnAction(event -> handleTurtleImageClick());
    button.setGraphic(createImageView(TURTLE_ICON_IMAGE));
    gridPane.add(button, 2, 0, 1, 1);
  }

  private void addLanguageDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(idBundle.getString("LanguageDropdown"));
    for (String language : languageBundle.keySet()) {
      comboBox.getItems().add(languageBundle.getString(language));
    }
    comboBox.setValue("English");
    gridPane.add(comboBox, 3, 0, 4, 1);
  }

  private void addHelpDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setId(idBundle.getString("HelpDropdown"));
    File folder = new File(REFERENCES_PATH);
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

  private void handlePenColorClick() {
    penColorButton.setVisible(false);
    penColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(penColor);
    colorPicker.setOnAction(event -> handlePenColorPicker(colorPicker));
    gridPane.add(colorPicker, 0, 0, 1, 1);
  }

  private void handlePenColorPicker(ColorPicker colorPicker) {
    penColor = colorPicker.getValue();
    turtleDisplay.setPenColor(penColor);
    gridPane.getChildren().remove(colorPicker);
    penColorButton.setVisible(true);
    penColorButton.setDisable(false);
  }

  private void handleBackgroundColorClick() {
    backgroundColorButton.setVisible(false);
    backgroundColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(backgroundColor);
    colorPicker.setOnAction(event -> handleBackgroundColorPicker(colorPicker));
    gridPane.add(colorPicker, 1, 0, 1, 1);
  }

  private void handleBackgroundColorPicker(ColorPicker colorPicker) {
    backgroundColor = colorPicker.getValue();
    turtleDisplay.setBackgroundColor(backgroundColor);
    gridPane.getChildren().remove(colorPicker);
    backgroundColorButton.setVisible(true);
    backgroundColorButton.setDisable(false);
  }

  private void handleTurtleImageClick() {
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

  private void handleHelpClick(String command) {
    try {
      String text = new String(Files.readAllBytes(Paths.get(REFERENCES_PATH + "/" + command)));
      Alert alert = new Alert(AlertType.INFORMATION, text);
      alert.setHeaderText(command);
      alert.showAndWait();
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR, resourceBundle.getString("HelpError"));
      alert.showAndWait();
    }
  }

  private ImageView createImageView(String filePath) {
    Image image = new Image(filePath, ICON_WIDTH, ICON_HEIGHT, false, false);
    ImageView imageView = new ImageView(image);
    return imageView;
  }
}
