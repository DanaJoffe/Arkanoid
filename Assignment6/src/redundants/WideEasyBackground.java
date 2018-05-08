package redundants;

import biuoop.DrawSurface;
import geometryprimitives.Circle;
import geometryprimitives.Line;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import sprites.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class WideEasyBackground implements Sprite {
    private Rectangle surface;
    private List<Line> lines;
    private List<Circle> circles;
    /**
     * constructor.
     * @param rect a rectangle that represents the boundaries of the background.
     */
    public WideEasyBackground(Rectangle rect) {
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
     * the method sets the lines that will be drawn.
     */
    private void setLines() {
        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);
        int x = (int) (this.getSurface().getUpperLeft().getX());
        int y = (int) (this.getSurface().getUpperLeft().getY());
        Point center = new Point(x + (2.5 * obWidth), y + (4 * obHeight));

        Point left = new Point(x, y + (8 * obHeight));
        Point right = new Point(x + this.getSurface().getWidth() - (1.5 * obWidth), y + (8 * obHeight));

        this.lines = new ArrayList<Line>();
        while (right.getX() > left.getX() - 20) {
            Line l = new Line(center, new Point(right.getX(), right.getY()));
            this.lines.add(l);
            right = new Point(right.getX() - 7, right.getY());
        }
    }
    /**
     * the method sets the circles that will be drawn.
     */
    private void setCircles() {
        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);
        int x = (int) (this.getSurface().getUpperLeft().getX());
        int y = (int) (this.getSurface().getUpperLeft().getY());
        Point center = new Point(x + (2.5 * obWidth), y + (4 * obHeight));

        double r = 2.28 * obHeight;
        this.circles = new ArrayList<Circle>();
        for (int i = 0; i < 3; i++) {
            Circle c = new Circle(center, (r));
            this.circles.add(c);
            r -= (0.365 * obHeight);
        }
    }

    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        double x = this.surface.getUpperLeft().getX();
        double y = this.surface.getUpperLeft().getY();
        d.setColor(Color.WHITE);
        d.fillRectangle((int) x, (int) y, (int) this.surface.getWidth(), (int) this.surface.getHeight());

        int red = 239;
        int green = 231;
        int blue = 176;
        d.setColor(new Color(red, green, blue));
        for (Line l: this.lines) {
            Point start = l.start();
            Point end = l.end();
            d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
        }

        int i = 0;
        d.setColor(this.getCircleColor(i));
        for (Circle c: this.circles) {
            Point center = c.getCenter();
            int r = (int) c.getRadius();
            d.setColor(this.getCircleColor(i));
            d.fillCircle((int) center.getX(), (int) center.getY(), r);
            i++;
        }
    }
    /**
     * @param index circle creation number.
     * @return the correct color for the circle.
     */
    private Color getCircleColor(int index) {
        int red = 225;
        int green = 225;
        int blue = 24;
        if (index == 1) {
            red = 236;
            green = 215;
            blue = 73;
        }
        if (index == 2) {
            red = 255;
            green = 225;
            blue = 24;
        }
        if (index == 0) {
            red = 239;
            green = 231;
            blue = 176;
        }
        return new Color(red, green, blue);
    }
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }
}