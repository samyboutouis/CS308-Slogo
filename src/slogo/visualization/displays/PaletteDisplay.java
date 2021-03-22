package slogo.visualization.displays;

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import slogo.visualization.CustomGridPane;
import slogo.visualization.Workspace;

public class PaletteDisplay extends ScrollingDisplay {

  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final ResourceBundle PALETTE_BUNDLE = ResourceBundle.getBundle("resources/parameters/DefaultPalette");
  private static final String RESOURCES = "resources";
  private static final String PALETTE_TITLE = "PaletteTitle";
  private static final String PALETTE_BOX_ID = "PaletteBoxID";
  private static final String PALETTE_TAG_ID = "PaletteTagID";
  private static final String NULL = "null";
  private static final String[] DEFAULT_PALETTE_TAG_PROPERTIES = {"Default1", "Default2",
      "Default3", "Default4", "Default5", "Default6"};
  private static final int PALETTE_TAG_ROW_COUNT = 1;
  private static final int PALETTE_TAG_COL_COUNT = 6;
  private static final int PALETTE_TAG_PADDING_LENGTH = 5;

  private final VBox paletteBox;

  public PaletteDisplay(Workspace workspace) {
    super(workspace);
    paletteBox = setupVBoxContainer(PALETTE_TITLE, PALETTE_BOX_ID);
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
    CustomGridPane paletteTag = new CustomGridPane(PALETTE_TAG_ROW_COUNT, PALETTE_TAG_COL_COUNT,
        PALETTE_TAG_PADDING_LENGTH);
    paletteTag.setId(ID_BUNDLE.getString(PALETTE_TAG_ID));

    Label indexLabel = new Label();
    Circle paletteCircle = new Circle();
    Label paletteLabel = new Label();

    paletteCircle.setRadius(12);
    paletteCircle.setStroke(Color.BLACK);
    paletteCircle.setStrokeWidth(2);

    paletteTag.add(indexLabel, 0, 0, 1, 1);
    paletteTag.add(paletteCircle, 1, 0, 1, 1);
    paletteTag.add(paletteLabel, 2, 0, 4, 1);

    paletteBox.getChildren().add(paletteTag);
    updatePaletteTag(index, r, g, b, imageName);
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
      String[] paletteValue = PALETTE_BUNDLE.getString(DEFAULT_PALETTE_TAG_PROPERTIES[i]).split(" ");
      paletteValue[3] = paletteValue[3].equals(NULL) ? null : paletteValue[3];
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
    return String.format("%s/%s", RESOURCES, paletteLabel.getText());
  }
}
