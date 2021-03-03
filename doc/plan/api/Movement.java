public class Movement {
  private double xPosition;
  private double yPosition;
  private double rotationDegree;
  private boolean isPenDown;

  public Movement (double x, double y, double angle, boolean penDown) {
    xPosition = x;
    yPosition = y;
    rotationDegree = angle;
    isPenDown = penDown;
  }
}
