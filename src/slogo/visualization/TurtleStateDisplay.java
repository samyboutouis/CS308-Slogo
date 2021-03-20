package slogo.visualization;

import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import slogo.FrontEndTurtleTracker;
import slogo.controller.FrontEndController;

public class TurtleStateDisplay implements TurtleObserver {

  private final VBox vbox;
  private final ButtonFactory buttonFactory;
  private final GridPane pane;
  private final FrontEndTurtleTracker turtleTracker;
  private ComboBox<String> turtleDropdown;
  private List<String> buttonList;

  public TurtleStateDisplay(FrontEndController frontEndController, FrontEndTurtleTracker frontEndTurtleTracker) {
    vbox = new VBox();
    buttonFactory = new ButtonFactory(frontEndController);
    buttonList = List.of("UpButton", "DownButton", "RightButton", "LeftButton");
    turtleTracker = frontEndTurtleTracker;
    pane = new GridPane();
    createComboBox();
    addFields();
    pane.add(vbox, 0, 0);
  }

  private void createComboBox() {
    turtleDropdown = new ComboBox<>();
    vbox.getChildren().add(turtleDropdown);
  }

  private void addFields() {
    for(String property : buttonList) {
      HBox hbox = new HBox();
      //hbox.getChildren().add(buttonFactory.createTextButton(property));
      vbox.getChildren().add(hbox);
    }
  }

  public void update(Object o) {
    turtleDropdown.getItems().clear();
    for(int id : (List<Integer>) o) {
      turtleDropdown.getItems().add(String.valueOf(id));
    }
  }

  public GridPane getPane() {
    return pane;
  }
}
