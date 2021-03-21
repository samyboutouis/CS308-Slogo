package slogo.visualization;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import slogo.FrontEndTurtleTracker;
import slogo.SafeTurtle;
import slogo.controller.FrontEndController;

public class TurtleStateDisplay implements TurtleObserver {
  private static final String LABEL_PROPERTY = "resources/reflection/TurtleStateLabels";
  private static final String COLORPICKER_ID = "PenColorPicker";
  private static final String SLIDER_ID = "Slider";
  private static final String PEN_UP = "PenUpButton";
  private static final String PEN_DOWN = "PenDownButton";
  private static final String IMAGE_BUTTON = "TurtleImageButton";

  private final VBox vbox;
  private final ButtonFactory buttonFactory;
  private final GridPane pane;
  private final FrontEndTurtleTracker turtleTracker;
  private final ResourceBundle labelBundle;
  private final List<String> labelList;
  private ComboBox<Integer> turtleDropdown;
  private int currentID;

  public TurtleStateDisplay(FrontEndController frontEndController, FrontEndTurtleTracker frontEndTurtleTracker) {
    vbox = new VBox();
    buttonFactory = new ButtonFactory(frontEndController);
    turtleTracker = frontEndTurtleTracker;
    turtleTracker.addObserver(this);
    pane = new GridPane();
    labelBundle = ResourceBundle.getBundle(LABEL_PROPERTY);
    labelList = List.of("X Position", "Y Position", "Direction", "Pen Status", "Pen Color", "Pen Width", "Image");
    currentID = 0;
    createComboBox();
    pane.add(vbox, 0, 0);
  }

  private void createComboBox() {
    turtleDropdown = new ComboBox<>();
    vbox.getChildren().add(turtleDropdown);
    turtleDropdown.valueProperty().addListener(event -> updateFields(turtleDropdown.getValue()));
  }

  public void updateTurtleNumber(List<Integer> list) {
    for(int id : list) {
      if(!turtleDropdown.getItems().contains(id)){
        turtleDropdown.getItems().add(id);
      }
    }
  }

  private void updateFields(int id) {
    currentID = id;
    vbox.getChildren().clear();
    vbox.getChildren().add(turtleDropdown);
    for(String property : labelList) {
      HBox hbox = new HBox();
      makeLabels(property, hbox, id);
      vbox.getChildren().add(hbox);
    }
  }

  private void makeLabels(String property, HBox hbox, int id) {
    hbox.getChildren().add(new Label(property + ": "));
    try {
      Method m = this.getClass()
        .getDeclaredMethod(labelBundle.getString(property), SafeTurtle.class, HBox.class);
      m.invoke(this, turtleTracker.getSafeTurtle(id), hbox);
    } catch (Exception e) {
      throw new RuntimeException("Improper configuration", e);
    }
  }

  public void updateTurtleState(int id) {
    if(id == currentID) {
      updateFields(id);
    }
  }

  public GridPane getPane() {
    return pane;
  }

  private void getX(SafeTurtle safeTurtle, HBox hBox) {
    double info = safeTurtle.getX();
    hBox.getChildren().add(new Text(String.format("%.2f", info)));
  }

  private void getY(SafeTurtle safeTurtle, HBox hBox) {
    double info = safeTurtle.getY();
    hBox.getChildren().add(new Text(String.format("%.2f", info)));
  }

  private void getDirection(SafeTurtle safeTurtle, HBox hBox) {
    double info = safeTurtle.getDirection();
    hBox.getChildren().add(new Text(String.format("%.2f", info)));
  }

  private void isPenDown(SafeTurtle safeTurtle, HBox hBox) {
    Button button;
    if(safeTurtle.isPenDown()){
      button = buttonFactory.createTurtleButton(PEN_UP, safeTurtle);
    } else {
      button = buttonFactory.createTurtleButton(PEN_DOWN, safeTurtle);
    }
    hBox.getChildren().add(button);
  }

  private void getPenColor(SafeTurtle safeTurtle, HBox hBox) {
    Color color = safeTurtle.getPenColor();
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId(COLORPICKER_ID);
    colorPicker.setValue(color);
    colorPicker.setOnAction(event -> safeTurtle.setPenColor(colorPicker.getValue()));
    hBox.getChildren().add(colorPicker);
  }

  private void getPenThickness(SafeTurtle safeTurtle, HBox hBox) {
    double thickness = safeTurtle.getPenThickness();
    Slider slider = new Slider(1, 5, thickness);
    slider.setId(SLIDER_ID);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(1);
    slider.valueProperty().addListener(event -> safeTurtle.setPenThickness(slider.getValue()));
    hBox.getChildren().add(slider);
  }

  private void setImage(SafeTurtle safeTurtle, HBox hBox) {
    Button button = buttonFactory.createTurtleButton(IMAGE_BUTTON, safeTurtle);
    hBox.getChildren().add(button);
  }
}
