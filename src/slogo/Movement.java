package slogo;

public class Movement {

  private double xCor;
  private double yCor;
  private double rotation;
  private boolean isPenDown;

  public Movement(double x, double y, double rotation, boolean isPenDown){
    xCor = x;
    yCor = y;
    this.rotation = rotation;
    this.isPenDown = isPenDown;
  }

}
