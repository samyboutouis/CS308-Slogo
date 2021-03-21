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

    addNewPaletteTag(1, 1, 1, 1);
  }

  public void addNewPaletteTag(int index, int r, int g, int b){
    CustomGridPane paletteTag = new CustomGridPane(PALETTE_TAG_ROW_COUNT, PALETTE_TAG_COL_COUNT, PALETTE_TAG_PADDING_LENGTH);
    paletteTag.setId(idBundle.getString(PALETTE_TAG_ID));

    Circle paletteCircle = new Circle();
    paletteCircle.setRadius(15);
    paletteCircle.setFill(Color.WHITE);
    paletteCircle.setStroke(Color.BLACK);
    paletteCircle.setStrokeWidth(2);
    Label paletteLabel = new Label("TEST");

    paletteTag.add(paletteCircle, 0, 0, 1, 1);
    paletteTag.add(paletteLabel, 1, 0, 5, 1);

    paletteBox.getChildren().add(paletteTag);
  }

}
