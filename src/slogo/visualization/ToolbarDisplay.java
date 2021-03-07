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

public class ToolbarDisplay {

  private final static int PADDING_LENGTH = 10;
  private static final String PAINT_BRUSH_IMAGE = "resources/paint-brush.png";
  private static final String PAINT_BUCKET_IMAGE = "resources/paint-bucket.png";
  private static final String TURTLE_ICON_IMAGE = "resources/turtle-icon.png";
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private final static int COLUMN_COUNT = 18;

  private final GridPane gridPane;
  private final ResourceBundle resourceBundle;

  public ToolbarDisplay(GridPane pane, String resourcePackage) {
    this.gridPane = pane;
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", "LanguageOptions"));
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
  }

  private void setScreen() {
    addPenColorButton();
    addBackgroundColorButton();
    addTurtleImageButton();
    addLanguageDropdown();
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
      resourceBundle.getString("English"),
      resourceBundle.getString("Chinese"),
      resourceBundle.getString("French"),
      resourceBundle.getString("German"),
      resourceBundle.getString("Italian"),
      resourceBundle.getString("Portuguese"),
      resourceBundle.getString("Russian"),
      resourceBundle.getString("Spanish"),
      resourceBundle.getString("Urdu")
    );
    comboBox.setValue("English");
    gridPane.add(comboBox, 3, 0, 4, 1);
  }

  private void handlePenColorClick() {

  }

  private void handleBackgroundColorClick() {

  }

  private void handleTurtleImageClick() {

  }

  private ImageView createImageView(String filePath) {
    Image image = new Image(filePath, ICON_WIDTH, ICON_HEIGHT, false, false);
    ImageView imageView = new ImageView(image);
    return imageView;
  }
}
