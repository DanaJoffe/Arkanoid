package characteristics;

import geometryprimitives.Point;

/**
 * @author joffeda.
 */
// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor.
     *@param dx x component A of speed.
     *@param dy y component A of speed.
     */

    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * gets angle and speed and creats a velocity.
     *@param angle the angle of the speed vector.
     *@param speed the size of the speed vector.
     *@return a new velocity.
     */

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angle2 = Velocity.angleTransform(angle);
        double dx = speed * Math.sin(Math.toRadians(angle2));
        double dy = speed * Math.cos(Math.toRadians(angle2));
        if (angle2 % 90 == 0) {
            double x = Math.floor(Math.sin(Math.toRadians(angle2)) * 100) / 100;
            double y = Math.floor(Math.cos(Math.toRadians(angle2)) * 100) / 100;
            dx = x * speed;
            dy = y * speed;
        }
        return new Velocity(dx, dy);
    }

    /**
     * gets an angle of a vector and finds the right angle for
     * the dx and dy calculations.
     * @param angle an angle.
     * @return the calculatable angle.
     */

    public static double angleTransform(double angle) {
        while (angle > 360) {
            angle -= 360;
        }
        while (angle < 0) {
            angle += 360;
        }
        return (180 - angle);
    }

    /**
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     *@param p a point (x,y) to change.
     *@return a new point with position (x+dx, y+dy).
     */

    public Point applyToPoint(Point p) {
        double x = p.getX();
        double y = p.getY();
        return new Point(x + this.dx , y + this.dy);
    }

    /**
     * @return the dx value.
     */

    public double dx() {
        return this.dx;
    }

    /**
     * @return the dy value.
     */

    public double dy() {
        return this.dy;
    }

    /**
     * @return the speed of the velocity; the equivalent vector to dx, dy.
     */
    public double getVectorSize() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }

    /**
     * angle = 0 when dx = 0 and dy < 0 (which means going straight up).
     * @return the angle of the velocity vector.
     */
    public double getAngle() {
        double ang = Math.toDegrees(Math.atan(this.dx() / this.dy()));
        if (dy > 0) {
            return (180 - ang);
        } else if (dy < 0) {
            return (-1 * ang);
        } else if (dy == 0) {
            if (this.dx() > 0) {
                return 90;
            }
            if (this.dx() < 0) {
                return -90;
            }
        }
        return 0;
    }
    /**
     * a test for the Velocity class.
     *@param args ignored..
     */

    public static void main(String[] args) {
        Velocity v = Velocity.fromAngleAndSpeed(30, 3);
        System.out.println("dx= " + v.dx() + ", dy= " + v.dy());
    }
}