package slogo;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Turtle {

  private static final String DEFAULT_IMAGE = "resources/turtle.png";
  private static final int IMAGE_HEIGHT = 50;
  private static final int IMAGE_WIDTH = 50;

  private ImageView imageView;
  private final GridPane gridPane;
  private double xCoordinate;
  private double yCoordinate;
  private double direction;
  private final Pen pen;

  public Turtle(GridPane gridPane) {
    this.gridPane = gridPane;
    xCoordinate = 0;
    yCoordinate = 0;
    direction = 0;
    pen = new Pen(gridPane);
    setDefaultImage();
  }

  public void forward(double pixels) {
    double xChange = Math.sin(Math.toRadians(direction)) * pixels;
    double yChange = Math.cos(Math.toRadians(direction)) * pixels;
    pen.drawLine(imageView.getTranslateX(), imageView.getTranslateY(),
      imageView.getTranslateX() + xChange,
      imageView.getTranslateY() - yChange);
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate += xChange;
    yCoordinate += yChange;
  }

  public void back(double pixels) {
    double xChange = Math.sin(Math.toRadians(direction)) * pixels;
    double yChange = Math.cos(Math.toRadians(direction)) * pixels;
    pen.drawLine(imageView.getTranslateX(), imageView.getTranslateY(),
      imageView.getTranslateX() - xChange,
      imageView.getTranslateY() + yChange);
    imageView.setTranslateX(imageView.getTranslateX() - xChange);
    imageView.setTranslateY(imageView.getTranslateY() + yChange);
    xCoordinate -= xChange;
    yCoordinate -= yChange;
  }

  public void right(double directionChange) {
    direction += directionChange;
    imageView.setRotate(imageView.getRotate() + directionChange);
  }

  public void left(double directionChange) {
    direction -= directionChange;
    imageView.setRotate(imageView.getRotate() - directionChange);
  }

  public void setDirection(double direction) {
    this.direction = direction;
    imageView.setRotate(direction);
  }

  public void setXY(double xPosition, double yPosition) {
    xCoordinate = xPosition;
    yCoordinate = yPosition;
    imageView.setTranslateX(xPosition);
    imageView.setTranslateY(yPosition);
  }

  public void show() {
    imageView.setVisible(true);
  }

  public void hide() {
    imageView.setVisible(false);
  }

  public void setImage(File file) {
    Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    imageView.setImage(image);
  }

  private void setDefaultImage() {
    Image image = new Image(DEFAULT_IMAGE, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    imageView = new ImageView(image);
    gridPane.add(imageView, 0, 0);
  }

  public void setPenColor(Color color) {
    pen.setColor(color);
  }

  public void penDown() {
    pen.penDown();
  }

  public void penUp() {
    pen.penUp();
  }
}
