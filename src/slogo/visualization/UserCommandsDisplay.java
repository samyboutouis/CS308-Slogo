package slogo.visualization;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserCommandsDisplay {
  private final VBox pane;
  public UserCommandsDisplay(VBox pane){
    this.pane = pane;

    Text test = new Text("users_command");
    pane.getChildren().add(test);
  }
}
