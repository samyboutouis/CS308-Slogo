package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PaletteDisplay extends ScrollingDisplay {

  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final String DEFAULT_PALETTE_PROPERTY = "resources/parameters/DefaultPalette";
  private final static String PALETTE_TITLE = "PaletteTitle";
  private final static String PALETTE_BOX_ID = "PaletteBoxID";
  private final static String PALETTE_TAG_ID = "PaletteTagID";

  private final static String[] DEFAULT_PALETTE_TAG_PROPERTIES = {"Default1", "Default2", "Default3", "Default4", "Default5"};

  private final static int PALETTE_TAG_ROW_COUNT = 1;
  private final static int PALETTE_TAG_COL_COUNT = 6;
  private final static int PALETTE_TAG_PADDING_LENGTH = 5;

  private final ResourceBundle idBundle;
  private final ResourceBundle paletteBundle;

  private final VBox paletteBox;

  public PaletteDisplay(Workspace workspace, String resourcePackage) {
    super(workspace, resourcePackage);
    paletteBox = setupVBoxContainer(PALETTE_TITLE, PALETTE_BOX_ID);

    idBundle = ResourceBundle.getBundle(ID_PROPERTY);
    paletteBundle = ResourceBundle.getBundle(DEFAULT_PALETTE_PROPERTY);

    addDefaultPaletteTags();
  }

  public void updatePaletteBox(int index, int r, int g, int b) {
    if (paletteBox.getChildren().size() < index) {
      addNewPaletteTag(index, r, g, b, null);
    } else {
      updatePaletteTag(index, r, g, b, null);
    }
  }

  private void addNewPaletteTag(int index, int r, int g, int b, String imageName) {
    CustomGridPane paletteTag = new CustomGridPane(PALETTE_TAG_ROW_COUNT, PALETTE_TAG_COL_COUNT, PALETTE_TAG_PADDING_LENGTH);
    paletteTag.setId(idBundle.getString(PALETTE_TAG_ID));

    Label indexLabel = new Label();
    Circle paletteCircle = new Circle();
    Label paletteLabel = new Label();

    updatePaletteTag(index, r, g, b, imageName);

    paletteCircle.setRadius(12);
    paletteCircle.setStroke(Color.BLACK);
    paletteCircle.setStrokeWidth(2);

    paletteTag.add(indexLabel, 0, 0, 1, 1);
    paletteTag.add(paletteCircle, 1, 0, 1, 1);
    paletteTag.add(paletteLabel, 2, 0, 4, 1);

    paletteBox.getChildren().add(paletteTag);
  }

  private void updatePaletteTag(int index, int r, int g, int b, String imageName) {
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Label indexLabel = (Label) paletteTag.getChildren().get(0);
    Circle paletteCircle = (Circle) paletteTag.getChildren().get(1);
    Label paletteLabel = (Label) paletteTag.getChildren().get(2);

    indexLabel.setText(String.format(" %d:", index));
    paletteCircle.setFill(Color.rgb(r, g, b));
    paletteLabel.setText(
        imageName == null ? String.format("%d,%d,%d", r, g, b) : String.format("%s", imageName)
    );
  }

  private void addDefaultPaletteTags() {
    for (int i = 0; i < DEFAULT_PALETTE_TAG_PROPERTIES.length; i++) {
      String[] paletteValue = paletteBundle.getString(DEFAULT_PALETTE_TAG_PROPERTIES[i]).split(" ");
      paletteValue[3] = paletteValue[3].equals("null") ? null : paletteValue[3];
      addNewPaletteTag(
          i + 1,
          Integer.parseInt(paletteValue[0]),
          Integer.parseInt(paletteValue[1]),
          Integer.parseInt(paletteValue[2]),
          paletteValue[3]
      );
    }
  }

  /**
   * @param index
   * @return
   */
  public Color getColorFromIndex(int index) {
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Circle paletteCircle = (Circle) paletteTag.getChildren().get(1);
    return (Color) paletteCircle.getFill();
  }

  /**
   * @param index
   * @return
   */
  public String getImagePathFromIndex(int index) {
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Label paletteLabel = (Label) paletteTag.getChildren().get(2);
    return String.format("resources/%s", paletteLabel.getText());
  }
}
