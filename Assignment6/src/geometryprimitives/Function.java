package geometryprimitives;

/**
 *author Dana Joffe.
 */
public class Function {
    private Double incline;
    private double b;
    private String type;

    /**
    * constructor.
    *@param line a segment on t he endless line function.
    */
    public Function(Line line) {
        this.incline = line.incline();
        if (this.incline.equals(Double.POSITIVE_INFINITY)) {
            this.b = line.start().getX();
            type = "x=";
        } else if (this.incline.equals(Double.valueOf(0))) {
            this.b = line.start().getY();
            type = "y=";
        } else {
            b = (-this.incline) * line.start().getX() + line.start().getY();
            type = "f(x)=";
        }
    }
    /**
    * @return b value.
    */

    public double b() {
        return this.b;
    }
    /**
    * @return type of function.
    */

    public String type() {
        return this.type;
    }
    /**
     * @return the incline of the function.
     */

    public Double incline() {
        return this.incline;
    }
    /**
     * the method will be activated on functions with different inclines.
     * @param other function.
     * @return the intersection point.
     */

    public Point intersectionPoint(Function other) {
        double y;
        if (this.type == "x=" || other.type() == "x=") { //one function is "x="
            if (this.type == "x=") { //this is "x=" and other is not
                y = other.yForX(this.b);
                return new Point(this.b , y);
            } else { //other is "x=" and this is not
                y = this.yForX(other.b());
                return new Point(other.b() , y);
            }
        }
        //this and other are both not "x="
        double x = this.xIntersection(other);
        y = this.yForX(x);
        return new Point(x , y);
    }
    /**
     * the function will be activated on "y=" or on "f(x)=" type..
     * @param x a value.
     * @return the y value of the function for x given.
     */

    public double yForX(double x) {
        return (this.incline * x + this.b);
    }
    /**
     * the function will be activated on "y=" or on "f(x)=" type.
     * @param other a function.
     * @return the x value of the functions intersection.
     */

    public double xIntersection(Function other) {
        //fact: inclines are different
        double x = (other.b() - this.b) / (this.incline - other.incline());
        return x;
    }
}
