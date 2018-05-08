package geometryprimitives;
import java.util.ArrayList;

/**
 * @author joffeda.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * constructor.
     * @param width the rectangle width.
     * @param height the rectangle height.
     * @param upperLeft the upperLeft point of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.height = height;
        this.width = width;
        this.upperLeft = upperLeft;
    }

    /**
     * @param line a line.
     * @return a (possibly empty) List of intersection points
     * with the specified line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionList = new ArrayList<Point>();
        Line up = new Line(this.upperLeft, new Point(this.rBorder(),
                this.upBorder()));
        Line down = new Line(new Point(this.rBorder(), this.bottomBorder()),
                new Point(this.lBorder(), this.bottomBorder()));
        Line right = new Line(new Point(this.rBorder(), this.upBorder()),
                new Point(this.rBorder(), this.bottomBorder()));
        Line left = new Line(this.upperLeft, new Point(this.lBorder(),
                this.bottomBorder()));
        Line[] lineArray = new Line[4];
        lineArray[0] = up;
        lineArray[1] = down;
        lineArray[2] = right;
        lineArray[3] = left;
        for (int i = 0; i < 4; ++i) {
            if (lineArray[i].isIntersecting(line)) {
                intersectionList.add(lineArray[i].intersectionWith(line));
            }
        }
        return intersectionList;
    }
    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     *@return the left border value.
     */
    public double lBorder() {
        return this.upperLeft.getX();
    }

    /**
     *@return the right border value.
     */
    public double rBorder() {
        return this.upperLeft.getX() + this.width;
    }

    /**
     *@return the bottom border value.
     */
    public double bottomBorder() {
        return this.upperLeft.getY() + this.height;
    }

    /**
     *@return the upper border value.
     */

    public double upBorder() {
        return this.upperLeft.getY();
    }
}
