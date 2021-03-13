package slogo;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Turtle {

  private static final String DEFAULT_IMAGE = "resources/turtle.png";
  private static final int IMAGE_HEIGHT = 50;
  private static final int IMAGE_WIDTH = 50;
  private static final int INITIAL_WIDTH = 320;
  private static final int INITIAL_HEIGHT = 275;

  private ImageView imageView;
  private final AnchorPane anchorPane;
  private double xCoordinate;
  private double yCoordinate;
  private double direction;
  private final Pen pen;
  private final ResourceBundle idBundle;

  public Turtle(AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
    xCoordinate = 0;
    yCoordinate = 0;
    direction = 0;
    pen = new Pen(anchorPane);
    this.idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", "resources", "stylesheets", "CSS_IDs"));
    setDefaultImage();
  }

  public void forward(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    double penX = imageView.getX() + imageView.getTranslateX() + IMAGE_WIDTH/2.0;
    double penY = imageView.getY() + imageView.getTranslateY() + IMAGE_HEIGHT/2.0;
    pen.drawLine(penX, penY, xChange, -yChange);
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate += xChange;
    yCoordinate += yChange;
  }

  public void back(double pixels) {
    double xChange = calculateComponentX(pixels);
    double yChange = calculateComponentY(pixels);
    double penX = imageView.getX() + imageView.getTranslateX() + IMAGE_WIDTH/2.0;
    double penY = imageView.getY() + imageView.getTranslateY() + IMAGE_HEIGHT/2.0;
    pen.drawLine(penX, penY, -xChange, yChange);
    imageView.setTranslateX(imageView.getTranslateX() - xChange);
    imageView.setTranslateY(imageView.getTranslateY() + yChange);
    xCoordinate -= xChange;
    yCoordinate -= yChange;
  }

  private double calculateComponentX(double pixels) {
    return Math.sin(Math.toRadians(direction)) * pixels;
  }

  private double calculateComponentY(double pixels) {
    return Math.cos(Math.toRadians(direction)) * pixels;
  }

  public void rotate(double directionChange) {
    direction += directionChange;
    imageView.setRotate(imageView.getRotate() + directionChange);
  }

  public void setDirection(double direction) {
    this.direction = direction;
    imageView.setRotate(direction);
  }

  public void setXY(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    imageView.setTranslateX(imageView.getTranslateX() + xChange);
    imageView.setTranslateY(imageView.getTranslateY() - yChange);
    xCoordinate = xPosition;
    yCoordinate = yPosition;
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
    imageView.setTranslateX(INITIAL_WIDTH);
    imageView.setTranslateY(INITIAL_HEIGHT);
    imageView.setId(idBundle.getString("Turtle"));
    anchorPane.getChildren().add(imageView);
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

  public double getX() {
    return xCoordinate;
  }

  public double getY() {
    return yCoordinate;
  }

  public void home() {
    setXY(0, 0);
  }

  public void clearScreen() {
    home();
    pen.removeLines();
    setDirection(0);
  }
}