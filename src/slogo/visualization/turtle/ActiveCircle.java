package slogo.visualization.turtle;

import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class represents the ring around the turtle indicating whether it is active or not. Used to
 * create the visual representation of whether a turtle is active. It is not dependent on other
 * classes for it to function but assumes that a FrontEndTurtle accompanies it as well. Is
 * primarily controlled by a turtle and it is assumed it only belongs to one turtle.
 */
public class ActiveCircle {

  private static final String DEFAULT_IMAGE = "resources/active-circle.png";

  private final ResourceBundle idBundle;
  private ImageView imageView;
  private double xCoordinate;
  private double yCoordinate;

  /**
   * Constructor of class.
   *
   * @param width Width of image.
   * @param height Height of image.
   */
  public ActiveCircle(int width, int height) {
    this.idBundle = ResourceBundle
        .getBundle(String.format("%s/%s/%s", "resources", "stylesheets", "CSS_IDs"));
    setDefaultImage(width, height);
  }

  /**
   * Moves the circle on the screen with the turtle.
   *
   * @param xChange double representing how much the turtle changes in the x-direction.
   * @param yChange double representing how much the turtle changes in the y-direction.
   */
  public void updatePosition(double xChange, double yChange) {
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() + yChange);
    xCoordinate += xChange;
    yCoordinate -= yChange;
  }

  private void setDefaultImage(int width, int height) {
    Image image = new Image(DEFAULT_IMAGE, width, height, false, false);
    imageView = new ImageView(image);
    imageView.setId(idBundle.getString("ActiveCircleID"));
  }

  /**
   * Gets the image view associated with the active ring.
   *
   * @return ImageView object representing the active ring.
   */
  public ImageView getImageView() {
    return imageView;
  }

  /**
   * Sets the active ring to showing and visible on the screen.
   */
  public void show() {
    imageView.setVisible(true);
  }

  /**
   * Sets the active ring to hiding and visible on the screen.
   */
  public void hide() {
    imageView.setVisible(false);
  }

  /**
   * Moves the active ring to a certain x and y-coordinate.
   *
   * @param xPosition double representing the x-coordinate of the ring
   * @param yPosition double representing the y-coordinate of the ring
   */
  public void setXY(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate = xPosition;
    yCoordinate = yPosition;
  }
}
