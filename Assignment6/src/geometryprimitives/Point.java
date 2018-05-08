package geometryprimitives;
/**
 *author Dana Joffe.
 */
public class Point {
    private double x;
    private double y;

    /**
    * constructor.
    *@param x x value of point.
    *@param y value of point.
    */

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
    *@param other a point.
    *@return return the distance of this point to the other point.
    */

    public double distance(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }
    /**
     * @param other a point.
     * @return return true is the points are equal, false otherwise.
     */

    public boolean equals(Point other) {
        double x1 = this.x;
        double x2 = other.getX();
        double y1 = this.y;
        double y2 = other.getY();
        if (x1 == x2 && y1 == y2) {
            return true;
        }
        return false;
    }
    /**
     * @return return the x value of this point.
     */

    public double getX() {
        return this.x;
    }
    /**
     * @return return the y value of this point.
     */

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        String write = "(";
        write = write.concat(String.valueOf(this.getX())).concat(", ").
                concat(String.valueOf(this.getY())).concat(")");
        return write;
    }
}