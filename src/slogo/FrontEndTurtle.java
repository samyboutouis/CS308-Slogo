package slogo;

import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import slogo.visualization.Pen;

public class FrontEndTurtle implements Turtle {

  private static final String DEFAULT_IMAGE = "resources/turtle.png";
  private static final int IMAGE_HEIGHT = 50;
  private static final int IMAGE_WIDTH = 50;

  private ImageView imageView;
  private double xCoordinate;
  private double yCoordinate;
  private double direction;
  private Pen pen;
  private final ResourceBundle idBundle;

  public FrontEndTurtle() {
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

  public void towards(double xPosition, double yPosition) {
    double xChange = xPosition - xCoordinate;
    double yChange = yPosition - yCoordinate;
    double direction = Math.toDegrees(Math.atan2(xChange, yChange));
    setDirection(direction);
  }

  public void show() {
    imageView.setVisible(true);
  }

  public void hide() {
    imageView.setVisible(false);
  }

  public boolean isShowing() {
    return imageView.isVisible();
  }

  public void setImage(File file) {
    Image image = new Image(file.toURI().toString(), IMAGE_WIDTH, IMAGE_HEIGHT, true, false);
    imageView.setImage(image);
  }

  private void setDefaultImage() {
    Image image = new Image(DEFAULT_IMAGE, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
    imageView = new ImageView(image);
    imageView.setId(idBundle.getString("Turtle"));
  }

  public void addToScreen(Pane turtlePane, double height, double width) {
    imageView.setTranslateX(width/2 - IMAGE_WIDTH/2);
    imageView.setTranslateY(height/2 - IMAGE_HEIGHT/2);
    turtlePane.getChildren().add(imageView);
    pen = new Pen(turtlePane, idBundle);
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

  public double getDirection() { return direction; }

  public boolean isPenDown() { return false; }

  public void home() {
    setXY(0, 0);
  }

  public void clearScreen() {
    home();
    pen.removeLines();
    setDirection(0);
  }
}