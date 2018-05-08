package geometryprimitives;

/**
 * @author joffeda.
 */
public class Circle {
    private double radius;
    private Point center;

    /**
     * constructor.
     * @param p center point of the circle.
     * @param r radius of the circle.
     */
    public Circle(Point p, double r) {
        this.center = p;
        this.radius = r;
    }

    /**
     * @return the radius of the circle.
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * @return the center point of the circle.
     */
    public Point getCenter() {
        return this.center;
    }
}
