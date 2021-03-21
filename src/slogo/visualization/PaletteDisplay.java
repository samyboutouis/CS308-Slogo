package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PaletteDisplay extends ScrollingDisplay {

  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private final static String PALETTE_TITLE = "PaletteTitle";
  private final static String PALETTE_BOX_ID = "PaletteBoxID";
  private final static String PALETTE_TAG_ID = "PaletteTagID";

  private final static int PALETTE_TAG_ROW_COUNT = 1;
  private final static int PALETTE_TAG_COL_COUNT = 6;
  private final static int PALETTE_TAG_PADDING_LENGTH = 5;

  private final ResourceBundle idBundle;
  private final ResourceBundle resourceBundle;

  private final VBox paletteBox;

  public PaletteDisplay(Workspace workspace, String resourcePackage){
    super(workspace, resourcePackage);
    paletteBox = setupVBoxContainer(PALETTE_TITLE, PALETTE_BOX_ID);
    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);

    addDefaultPaletteTags();
  }

  /**
   *
   * @param index
   * @param r
   * @param g
   * @param b
   */
  public void updatePaletteBox(int index, int r, int g, int b){
    if(paletteBox.getChildren().size() < index){
      addNewPaletteTag(index, r, g, b, null);
    } else {
      updatePaletteTag(index, r, g, b);
    }
  }

  private void addNewPaletteTag(int index, int r, int g, int b, String imageName){
    CustomGridPane paletteTag = new CustomGridPane(PALETTE_TAG_ROW_COUNT, PALETTE_TAG_COL_COUNT, PALETTE_TAG_PADDING_LENGTH);
    paletteTag.setId(idBundle.getString(PALETTE_TAG_ID));

    Label indexLabel = new Label(String.format(" %d:", index));
    Circle paletteCircle = new Circle();
    Label paletteLabel = new Label(imageName == null ? String.format("%d,%d,%d", r, g, b) : String.format("%s", imageName));

    paletteCircle.setRadius(12);
    paletteCircle.setFill(Color.rgb(r, g, b));
    paletteCircle.setStroke(Color.BLACK);
    paletteCircle.setStrokeWidth(2);

    paletteTag.add(indexLabel, 0, 0, 1, 1);
    paletteTag.add(paletteCircle, 1, 0, 1, 1);
    paletteTag.add(paletteLabel, 2, 0, 4, 1);

    paletteBox.getChildren().add(paletteTag);
  }

  private void addDefaultPaletteTags(){
    String[] defaultImageNames = {"paint-brush.png", "paint-bucket.png", "turtle.png", "turtle-icon.png"};
    for(int i = 0; i < defaultImageNames.length; i++){
      addNewPaletteTag(i + 1, 255, 255, 255, defaultImageNames[i]);
    }
  }

  private void updatePaletteTag(int index, int r, int g, int b){
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Label indexLabel = (Label) paletteTag.getChildren().get(0);
    Circle paletteCircle = (Circle) paletteTag.getChildren().get(1);
    Label paletteLabel = (Label) paletteTag.getChildren().get(2);

    indexLabel.setText(String.format(" %d:", index));
    paletteCircle.setFill(Color.rgb(r, g, b));
    paletteLabel.setText(String.format("%d,%d,%d", r, g, b));
  }

  /**
   *
   * @param index
   * @return
   */
  public Color getColorFromIndex(int index){
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Circle paletteCircle = (Circle) paletteTag.getChildren().get(1);
    return (Color) paletteCircle.getFill();
  }

  /**
   *
   * @param index
   * @return
   */
  public String getImagePathFromIndex(int index){
    CustomGridPane paletteTag = (CustomGridPane) paletteBox.getChildren().get(index - 1);
    Label paletteLabel = (Label) paletteTag.getChildren().get(2);
    return String.format("resources/%s", paletteLabel.getText());
  }
}
