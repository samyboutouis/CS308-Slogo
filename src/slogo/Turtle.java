package slogo;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends ImageView {

  private static final String DEFAULT_IMAGE = "resources/turtle.png";
  private static final int IMAGE_HEIGHT = 50;
  private static final int IMAGE_WIDTH = 50;

  private double xCoordinate;
  private double yCoordinate;
  private double direction;

  public Turtle() {
    xCoordinate = 0;
    yCoordinate = 0;
    direction = 0;
    setDefaultImage();
  }

  public void forward(double pixels) {
    double xChange = Math.sin(Math.toRadians(direction)) * pixels;
    double yChange = Math.cos(Math.toRadians(direction)) * pixels;
    this.setTranslateX(xChange);
    this.setTranslateY(-yChange);
    xCoordinate += xChange;
    yCoordinate += yChange;
  }

  public void back(double pixels) {
    double xChange = Math.sin(Math.toRadians(direction)) * pixels;
    double yChange = Math.cos(Math.toRadians(direction)) * pixels;
    this.setTranslateX(-xChange);
    this.setTranslateY(yChange);
    xCoordinate -= xChange;
    yCoordinate -= yChange;
  }

  public void right(double directionChange) {
    direction += directionChange;
  }

  public void left(double directionChange) {
    direction -= directionChange;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  public void setXY(double xPosition, double yPosition) {
    xCoordinate = xPosition;
    yCoordinate = yPosition;
    this.setX(xPosition);
    this.setY(yPosition);
  }

  public void show() {
    this.setVisible(true);
  }

  public void hide() {
    this.setVisible(false);
  }

  public void setImage(File file) {
    Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    this.setImage(image);
  }

  private void setDefaultImage() {
    Image image = new Image(DEFAULT_IMAGE, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    this.setImage(image);
  }
}
