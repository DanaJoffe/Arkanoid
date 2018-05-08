package geometryprimitives;

import java.util.List;

/**
 * @author joffeda.
 */
public class Line {
    private Point start;
    private Point end;
    private Function func;
    /**
    * constructor.
    * @param start a point.
    * @param end a point.
    */

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.func = new Function(this);
    }
    /**
     * constructor.
     *@param x1 a x value of start point.
     *@param y1 a y value of start point.
     *@param x2 a x value of end point.
     *@param y2 a y value of end point.
     */

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1 , y1);
        this.end = new Point(x2 , y2);
        this.func = new Function(this);
    }

    /**
     *@return return the length of the line.
     */

    public double length() {
        return this.start.distance(this.end);
    }

    /**
     *@return return the middle point of the line.
     */

    public Point middle() {
        double xMid = (this.start.getX() + this.end.getX()) / 2;
        double yMid = (this.start.getY() + this.end.getY()) / 2;
        return new Point(xMid , yMid);
    }
    /**
     *@return return the start point of the line.
     */

    public Point start() {
        return this.start;
    }

    /**
     *@return return the end point of the line.
     */

    public Point end() {
        return this.end;
    }
    /**
     *@return the function representing the line.
     */

    public Function func() {
        return this.func;
    }
    /**
     *@return returns true if the lines intersect, false otherwise.
     *@param other line.
     */

    public boolean isIntersecting(Line other) {
        Point p = this.intersectionWith(other);
        if (p == null) { //no intersection exists
            return false;
        }
        //intersection exists
        return true;
    }

    /**
     *@return returns the intersection point if the lines intersect,
     *and null otherwise.
     *@param other line.
     */

    public Point intersectionWith(Line other) {
        if (this.equals(other)) { //lines are the same
            return null;
        }
        if (this.incline().equals(other.incline())) { //lines have the same incline
            return this.intersectionSameIncline(other);
        }
        //endless lines that don't have the same incline- must intersect
        Point linesInterPoint = this.func.intersectionPoint(other.func());
        //if the intersection point is in the domain of both segments
        if (this.isInLineDomain(linesInterPoint)
                && other.isInLineDomain(linesInterPoint)) {
            return linesInterPoint;
        }
        return null;
    }
    /**
     * the method will be activated on functions with the same incline.
     * @param other a line.
     * @return the intersection point. null if there isn't one.
     */

    public Point intersectionSameIncline(Line other) {
        Point s1 = this.start;
        Point e1 = this.end;
        Point s2 = other.start();
        Point e2 = other.end();
        double dist = this.length() + other.length();
        if (s1.equals(s2) && e1.distance(e2) == dist) {
            return s1;
        }
        if (e1.equals(e2) && s1.distance(s2) == dist) {
            return e1;
        }
        if (s1.equals(e2) && e1.distance(s2) == dist) {
            return s1;
        }
        if (e1.equals(s2) && s1.distance(e2) == dist) {
            return e1;
        }
        return null;
    }
    /**
     * @param point a point.
     * @return true if the point is in the line's domain.
     */

    public boolean isInLineDomain(Point point) {
        double x = point.getX();
        double y = point.getY();
        double xMax = Math.max(this.start.getX() , this.end.getX());
        double xMin = Math.min(this.start.getX() , this.end.getX());
        double yMax = Math.max(this.start.getY() , this.end.getY());
        double yMin = Math.min(this.start.getY() , this.end.getY());
        if (x <= xMax && x >= xMin && y <= yMax && y >= yMin) {
            return true;
        }
        return false;
    }
    /**
     * @return the incline of the line.
     */

    public Double incline() {
        Double deltaY = Double.valueOf(this.start.getY() - this.end.getY());
        Double deltaX = Double.valueOf(this.start.getX() - this.end.getX());
        Double inc = deltaY / deltaX;
        if (inc == -Double.valueOf(0)) {
            inc = Double.valueOf(0);
        }
        if (inc == Double.NEGATIVE_INFINITY) {
            inc = Double.POSITIVE_INFINITY;
        }
        return inc;
    }
    /**
     *@param other a line.
     *@return return true is the lines are equal, false otherwise.
     */

    public boolean equals(Line other) {
        if (this.start.equals(other.start())
                && this.end.equals(other.end())) { //start with start
            return true;
        }
        if (this.end.equals(other.start())
                && this.start.equals(other.end())) { //start with end
            return true;
        }
        return false;
    }

    /**
     *@param rect a Rectangle object.
     *@return the closest intersection point to the start of the line.
     * If this line does not intersect with the rectangle, return null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPointsList = rect.intersectionPoints(this);
        if (interPointsList.size() == 0) {
            return null;
        }
        Point interPoint = interPointsList.get(0);
        double dist = this.start.distance(interPoint);
        for (int i = 0; i < interPointsList.size(); i++) {
            Point p = interPointsList.get(i);
            if (p.distance(this.start) < dist) {
                interPoint = p;
            }
        }
        return interPoint;
    }
}