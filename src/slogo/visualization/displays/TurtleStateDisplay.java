package slogo.visualization.displays;

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
import slogo.visualization.turtle.Turtle;
import slogo.visualization.FrontEndController;
import slogo.visualization.ButtonFactory;
import slogo.visualization.turtle.FrontEndTurtleTracker;
import slogo.visualization.observers.TurtleObserver;

/**
 * Display class that controls and displays a specific turtle's state information including
 * position, heading, line color and thickness, etc. Is dependent on the front-end controller and
 * turtle tracker classes to handle changes to the global workspace and list of turtles on the
 * screen.
 *
 * @author Samy Boutouis
 */
public class TurtleStateDisplay implements TurtleObserver {

  private static final String LABEL_PROPERTY = "resources/reflection/TurtleStateLabels";
  private static final String COLOR_PICKER_ID = "PenColorPicker";
  private static final String TURTLE_DROPDOWN = "TurtleDropdown";
  private static final String SLIDER_ID = "Slider";
  private static final String X_ID = "XPosition";
  private static final String Y_ID = "YPosition";
  private static final String DIRECTION = "Direction";
  private static final int SLIDER_MIN = 1;
  private static final int SLIDER_MAX = 5;
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

  /**
   * Constructor for the class.
   *
   * @param frontEndController FrontEndController for the entire workspace.
   * @param frontEndTurtleTracker TurtleTracker for the entire workspace tracking all the turtles
   */
  public TurtleStateDisplay(FrontEndController frontEndController,
      FrontEndTurtleTracker frontEndTurtleTracker) {
    vbox = new VBox();
    buttonFactory = new ButtonFactory(frontEndController);
    turtleTracker = frontEndTurtleTracker;
    turtleTracker.addObserver(this);
    pane = new GridPane();
    labelBundle = ResourceBundle.getBundle(LABEL_PROPERTY);
    labelList = List
        .of("X Position", "Y Position", "Direction", "Pen Status", "Pen Color", "Pen Width",
            "Image");
    currentID = 0;
    createComboBox();
    pane.add(vbox, 0, 0);
  }

  private void createComboBox() {
    turtleDropdown = new ComboBox<>();
    vbox.getChildren().add(turtleDropdown);
    turtleDropdown.valueProperty().addListener(event -> updateFields(turtleDropdown.getValue()));
    turtleDropdown.setId(TURTLE_DROPDOWN);
  }

  /**
   * Updates the list of turtles in the dropdown when the number of turtles on the screen update.
   * Is called by the turtle tracker using the TurtleObserver interface.
   *
   * @param list List representing all the IDs of turtles on the screen
   */
  public void updateTurtleNumber(List<Integer> list) {
    for (int id : list) {
      if (!turtleDropdown.getItems().contains(id)) {
        turtleDropdown.getItems().add(id);
      }
    }
  }

  private void updateFields(int id) {
    currentID = id;
    vbox.getChildren().clear();
    vbox.getChildren().add(turtleDropdown);
    for (String property : labelList) {
      HBox hbox = new HBox();
      makeLabels(property, hbox, id);
      vbox.getChildren().add(hbox);
    }
  }

  private void makeLabels(String property, HBox hbox, int id) {
    hbox.getChildren().add(new Label(property + ": "));
    try {
      Method m = this.getClass()
          .getDeclaredMethod(labelBundle.getString(property), Turtle.class, HBox.class);
      m.invoke(this, turtleTracker.getTurtle(id), hbox);
    } catch (Exception e) {
      throw new RuntimeException("Improper configuration", e);
    }
  }

  /**
   * Updates the turtle state view if a turtle is updated and showed on the dropdown. Is called by
   * the turtle tracker using the TurtleObserver interface.
   *
   * @param id Integer corresponding to an ID of a turtle
   */
  public void updateTurtleState(int id) {
    if (id == currentID) {
      updateFields(id);
    }
  }

  /**
   * Returns the root pane of the display view.
   *
   * @return Root pane of the display view
   */
  public GridPane getPane() {
    return pane;
  }

  private void getX(Turtle turtle, HBox hBox) {
    double info = turtle.getX();
    Text text = new Text(String.format("%.2f", info));
    text.setId(X_ID);
    hBox.getChildren().add(text);
  }

  private void getY(Turtle turtle, HBox hBox) {
    double info = turtle.getY();
    Text text = new Text(String.format("%.2f", info));
    text.setId(Y_ID);
    hBox.getChildren().add(text);
  }

  private void getDirection(Turtle turtle, HBox hBox) {
    double info = turtle.getDirection();
    Text text = new Text(String.format("%.2f", info));
    text.setId(DIRECTION);
    hBox.getChildren().add(text);
  }

  private void isPenDown(Turtle turtle, HBox hBox) {
    Button button;
    if (turtle.isPenDown()) {
      button = buttonFactory.createTurtleButton(PEN_UP, turtle);
    } else {
      button = buttonFactory.createTurtleButton(PEN_DOWN, turtle);
    }
    hBox.getChildren().add(button);
  }

  private void getPenColor(Turtle turtle, HBox hBox) {
    Color color = turtle.getPenColor();
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId(COLOR_PICKER_ID);
    colorPicker.setValue(color);
    colorPicker.setOnAction(event -> turtle.setPenColor(colorPicker.getValue()));
    hBox.getChildren().add(colorPicker);
  }

  private void getPenThickness(Turtle turtle, HBox hBox) {
    double thickness = turtle.getPenThickness();
    Slider slider = new Slider(SLIDER_MIN, SLIDER_MAX, thickness);
    slider.setId(SLIDER_ID);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(1);
    slider.valueProperty().addListener(event -> turtle.setPenThickness(slider.getValue()));
    hBox.getChildren().add(slider);
  }

  private void setImage(Turtle turtle, HBox hBox) {
    Button button = buttonFactory.createTurtleButton(IMAGE_BUTTON, turtle);
    hBox.getChildren().add(button);
  }
}
