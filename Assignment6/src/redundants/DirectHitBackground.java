package redundants;

import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Circle;
import geometryprimitives.Line;
import geometryprimitives.Rectangle;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * @author joffeda.
 */
public class DirectHitBackground implements Sprite {
    private Rectangle surface;
    private List<Line> lines;
    private List<Circle> circles;

    /**
     * constructor.
     * @param rect a rectangle that represents the boundaries of the background.
     */
    public DirectHitBackground(Rectangle rect) {
        this.surface = rect;
        this.setLines();
        this.setCircles();
    }

    /**
     * @return the rectangle that represents the boundaries of the background.
     */
    private Rectangle getSurface() {
        return this.surface;
    }

    /**
     * the method sets the lines of the drawing.
     */
    private void setLines() {
        double x = this.surface.getUpperLeft().getX();
        double y = this.surface.getUpperLeft().getY();
        int obHeight = (int) (this.getSurface().getWidth() / 25);
        Point center = new Point(x + (this.getSurface().getWidth() / 2),
                        y + (4 * obHeight));
        double halfDist = Math.min(center.getY() - y, center.getX() - x) + ((0.75) * obHeight);
        Point up = new Point(center.getX(), y);
        Point down = new Point(center.getX(), center.getY() + halfDist);
        Point right = new Point(center.getX() + halfDist, center.getY());
        Point left = new Point(center.getX() - halfDist, center.getY());
        Line l = new Line(up, down);
        Line l2 = new Line(right, left);

        this.lines = new ArrayList<Line>();
        this.lines.add(l);
        this.lines.add(l2);
    }

    /**
     * the method sets the circles of the drawing.
     */
    private void setCircles() {
        double x = this.surface.getUpperLeft().getX();
        double y = this.surface.getUpperLeft().getY();
        int obHeight = (int) (this.getSurface().getWidth() / 25);
        Point center = new Point(x + (this.getSurface().getWidth() / 2),
                        y + (4 * obHeight));
        this.circles = new ArrayList<Circle>();
        for (int i = 2; i < 5; i++) {
            Circle c = new Circle(center, (i * obHeight));
            this.circles.add(c);
        }
    }

    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        double x = this.surface.getUpperLeft().getX();
        double y = this.surface.getUpperLeft().getY();
        d.setColor(Color.BLACK);
        d.fillRectangle((int) x, (int) y, (int) this.surface.getWidth(), (int) this.surface.getHeight());

        d.setColor(Color.BLUE);
        for (Line l: this.lines) {
            Point start = l.start();
            Point end = l.end();
            d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
        }
        for (Circle c: this.circles) {
            Point center = c.getCenter();
            int r = (int) c.getRadius();
            d.drawCircle((int) center.getX(), (int) center.getY(), r);
        }
    }
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }
}