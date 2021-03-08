package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class ToolbarDisplay {

  private final static int PADDING_LENGTH = 10;
  private static final String PAINT_BRUSH_IMAGE = "resources/paint-brush.png";
  private static final String PAINT_BUCKET_IMAGE = "resources/paint-bucket.png";
  private static final String TURTLE_ICON_IMAGE = "resources/turtle-icon.png";
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private final static int COLUMN_COUNT = 10;

  private final GridPane gridPane;
  private final ResourceBundle languageBundle;
  private final ResourceBundle resourceBundle;

  public ToolbarDisplay(GridPane pane, String resourcePackage) {
    this.gridPane = pane;
    this.languageBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
    String language = "English";
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    initializeGridPane();
    setScreen();
  }

  private void initializeGridPane() {
    gridPane.setHgap(PADDING_LENGTH);
    gridPane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));
    for (int i = 0; i < COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      col.setPercentWidth(100.0 / COLUMN_COUNT);
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
    Button button = new Button();
    button.setOnAction(event -> handlePenColorClick());
    button.setGraphic(createImageView(PAINT_BRUSH_IMAGE));
    gridPane.add(button, 0, 0, 1, 1);
  }

  private void addBackgroundColorButton() {
    Button button = new Button();
    button.setOnAction(event -> handleBackgroundColorClick());
    button.setGraphic(createImageView(PAINT_BUCKET_IMAGE));
    gridPane.add(button, 1, 0, 1, 1);
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

  }

  private void handleBackgroundColorClick() {

  }

  private void handleTurtleImageClick() {

  }

  private void handleHelpClick() {

  }

  private ImageView createImageView(String filePath) {
    Image image = new Image(filePath, ICON_WIDTH, ICON_HEIGHT, false, false);
    ImageView imageView = new ImageView(image);
    return imageView;
  }
}
