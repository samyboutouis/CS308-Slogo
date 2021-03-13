package slogo;

public class CoordinateDirectionCommand implements Command{

    // deals with turtle call that sets direction to somewhere absolute using coordinates
    private double x;
    private double y;

    public CoordinateDirectionCommand(double x, double y){
      this.x = x;
      this.y = y;
    }

    @Override
    public void doCommand(Turtle turtle) {
      turtle.towards(x, y);
    }
  }
}
