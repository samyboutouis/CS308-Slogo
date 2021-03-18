package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ActiveCircle {
  private static final String DEFAULT_IMAGE = "resources/active-circle.png";

  private final ResourceBundle idBundle;
  private ImageView imageView;

  public ActiveCircle(int width, int height) {
    this.idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", "resources", "stylesheets", "CSS_IDs"));
    setDefaultImage(width, height);
  }

  public void updatePosition(double xChange, double yChange) {
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() + yChange);
  }

  private void setDefaultImage(int width, int height) {
    Image image = new Image(DEFAULT_IMAGE, width, height, false, false);
    imageView = new ImageView(image);
    imageView.setId(idBundle.getString("ActiveCircleID"));
  }

  public ImageView getImageView() {
    return imageView;
  }

  public void toggle() {
    imageView.setVisible(!imageView.isVisible());
  }
}
