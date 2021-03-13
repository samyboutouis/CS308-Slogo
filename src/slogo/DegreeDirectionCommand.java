package slogo;

public class DegreeDirectionCommand implements Command{
  // deals with turtle call that sets direction to somewhere absolute using angle
  private double degrees;

  public DegreeDirectionCommand(double degrees) {
    this.degrees = degrees;
  }

  @Override
  public void doCommand(Turtle turtle) {
    turtle.setDirection(degrees);
  }
}
