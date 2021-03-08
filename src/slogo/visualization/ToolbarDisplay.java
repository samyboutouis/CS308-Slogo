package slogo.visualization;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
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

  private final static int PADDING_LENGTH = 10;
  private static final String PAINT_BRUSH_IMAGE = "resources/paint-brush.png";
  private static final String PAINT_BUCKET_IMAGE = "resources/paint-bucket.png";
  private static final String TURTLE_ICON_IMAGE = "resources/turtle-icon.png";
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private final static int COLUMN_COUNT = 10;

  private final Stage stage;
  private final GridPane gridPane;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;
  private Button backgroundColorButton;
  private Button penColorButton;
  private Color backgroundColor;
  private Color penColor;

  public ToolbarDisplay(GridPane pane, String resourcePackage, Stage stage) {
    this.gridPane = pane;
    this.stage = stage;
    this.languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    String language = "English";
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
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
    addHelpButton();
  }

  private void addPenColorButton() {
    penColorButton = new Button();
    penColorButton.setOnAction(event -> handlePenColorClick());
    penColorButton.setGraphic(createImageView(PAINT_BRUSH_IMAGE));
    gridPane.add(penColorButton, 0, 0, 1, 1);
  }

  private void addBackgroundColorButton() {
    backgroundColorButton = new Button();
    backgroundColorButton.setOnAction(event -> handleBackgroundColorClick());
    backgroundColorButton.setGraphic(createImageView(PAINT_BUCKET_IMAGE));
    gridPane.add(backgroundColorButton, 1, 0, 1, 1);
  }

  private void addTurtleImageButton() {
    Button button = new Button();
    button.setOnAction(event -> handleTurtleImageClick());
    button.setGraphic(createImageView(TURTLE_ICON_IMAGE));
    gridPane.add(button, 2, 0, 1, 1);
  }

  private void addLanguageDropdown() {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(
      languageBundle.getString("English"),
      languageBundle.getString("Chinese"),
      languageBundle.getString("French"),
      languageBundle.getString("German"),
      languageBundle.getString("Italian"),
      languageBundle.getString("Portuguese"),
      languageBundle.getString("Russian"),
      languageBundle.getString("Spanish"),
      languageBundle.getString("Urdu")
    );
    comboBox.setValue("English");
    gridPane.add(comboBox, 3, 0, 4, 1);
  }

  private void addHelpButton() {
    Button button = new Button(resourceBundle.getString("HelpButton"));
    button.setOnAction(event -> handleHelpClick());
    gridPane.add(button, 9, 0, 2, 1);
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
      //FIXME: use new image file
    }
  }

  private void handleHelpClick() {

  }

  private ImageView createImageView(String filePath) {
    Image image = new Image(filePath, ICON_WIDTH, ICON_HEIGHT, false, false);
    ImageView imageView = new ImageView(image);
    return imageView;
  }
}
