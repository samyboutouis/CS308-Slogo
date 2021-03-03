package slogo.visualization;

import java.util.Map;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;

public interface Display {
  public void addToHistory(String input);
  // adds what is given as input to the history part of the Display

  public void showError(String error);
  // display error message to a separate window

  public void updateVariable(Map<String, Integer>);
  // update variable as defined by user and update the display accordingly

  public String receivePenColor();
  // Get the hex code of the color the user types in the UI

  public String receiveBackgroundColor();
  // Get the hex code of the background color the user types in the UI

  public void changePenColor(Color color);
  // Sets the turtle pen color to the inputted color

  public void changeBackgroundColor(Color color);
  // Sets the background color to the inputted color

  public Image getImage(FileChooser fileChooser);
  // gets the image selected from a file FileChooser

  public void setImage(Image image);
  // sets the image of the turtle to the image passed in

  public void openReference();
  // opens the reference page with help

  public void clearScreen();
  // resets the turtle position and clears user created variables and history
}
