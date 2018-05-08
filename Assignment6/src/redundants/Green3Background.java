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
public class Green3Background implements Sprite {
    private Rectangle surface;
    private List<Line> lines;
    private List<Circle> circles;
    private List<Rectangle> rectangle;
    /**
     * constructor.
     * @param rect a rectangle that represents the boundaries of the background.
     */
    public Green3Background(Rectangle rect) {
        this.surface = rect;
        this.rectangle = new ArrayList<Rectangle>();
        this.setLines();
    }
    /**
     * @return the rectangle that represents the boundaries of the background.
     */
    private Rectangle getSurface() {
        return this.surface;
    }
    /**
     * the method sets the lines that will be drawn.
     * this method calls setRectangles.
     */
    private void setLines() {
        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);
        int x = (int) (this.getSurface().getUpperLeft().getX());
        int y = (int) (this.getSurface().getUpperLeft().getY());
        Point downleft = new Point(x + (0.8 * obWidth), y + this.getSurface().getHeight());
        Point downright = new Point(downleft.getX() + (2 * obWidth), downleft.getY());
        Point upperleft = new Point(downleft.getX(), downleft.getY() - (6 * obHeight));
        Point upperright = new Point(downright.getX(), upperleft.getY());

        Rectangle rec = new Rectangle(upperleft, upperleft.distance(upperright),
                upperleft.distance(downleft));

        setRectangles(rec);
        int barsThick = obWidth / 6;
        int frameThick = barsThick + 3;
        double gap = (rec.getWidth() - (2 * frameThick) - (4 * barsThick)) / 5;

        this.lines = new ArrayList<Line>();
        for (int i = 0; i < frameThick; i++) { //frame walls.
            Line l = new Line(upperleft, upperright);
            Line l2 = new Line(upperleft, downleft);
            Line l3 = new Line(upperright, downright);
            this.lines.add(l);
            this.lines.add(l2);
            this.lines.add(l3);
            upperleft = new Point(upperleft.getX() + 1, upperleft.getY() + 1);
            upperright = new Point(upperright.getX() - 1, upperright.getY() + 1);
            downleft = new Point(downleft.getX() + 1, downleft.getY());
            downright = new Point(downright.getX() - 1, downright.getY());
        }

        Point right = new Point(upperright.getX(), upperright.getY());
        int j = 0;
        do { //bars.
            downright = new Point(downright.getX() - gap, downright.getY());
            right = new Point(right.getX() - gap, right.getY());
            upperleft = new Point(upperleft.getX(), upperleft.getY() + 0.95 * obHeight);
            upperright = new Point(upperright.getX(), upperright.getY() + 0.95 * obHeight);
            for (int k = 0; k < barsThick; k++) {
                Line l4 = new Line(upperleft, upperright);
                Line l5 = new Line(downright, right);
                this.lines.add(l4);
                this.lines.add(l5);
                upperleft = new Point(upperleft.getX(), upperleft.getY() + 1);
                upperright = new Point(upperright.getX(), upperright.getY() + 1);
                right = new Point(right.getX() - 1, right.getY());
                downright = new Point(downright.getX() - 1, downright.getY());
            }
            j++;
        } while(j < 4);
    }

    /**
     * the method sets the Rectangles that will be drawn.
     * this method calls setCircles
     * @param rec the basic Rectangle which will determine the position of the rest of the Rectangles.
     */
    private void setRectangles(Rectangle rec) {
        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);
        this.rectangle.add(rec);
        double mid = rec.getWidth() / 2;
        double halfRecWid = (obWidth * (0.625)) / 2;
        Point upperleft = new Point(rec.getUpperLeft().getX() + mid - halfRecWid,
                rec.getUpperLeft().getY() - (2 * obHeight));
        Rectangle r2 = new Rectangle(upperleft, 2 * halfRecWid, 2 * obHeight);
        this.rectangle.add(r2);

        mid = r2.getWidth() / 2;
        halfRecWid = obWidth * 0.11111111;
        upperleft = new Point(r2.getUpperLeft().getX() + mid - halfRecWid,
                r2.getUpperLeft().getY() - (8 * obHeight));
        Rectangle r3 = new Rectangle(upperleft, 2 * halfRecWid, 8 * obHeight);
        this.rectangle.add(r3);
        this.setCircles(new Point(upperleft .getX() + halfRecWid, upperleft.getY()));
    }

    /**
     * the method sets the Circles that will be drawn.
     * @param center the point will determine the center of all of the Circles.
     */
    private void setCircles(Point center) {
        int obHeight = (int) (this.getSurface().getHeight() / 22);

        double r = 0.5 * obHeight / 3;
        this.circles = new ArrayList<Circle>();
        for (int i = 3; i > 0; i--) {
            Circle c = new Circle(center, (r * i));
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
        d.setColor(new Color(42, 130, 21));
        d.fillRectangle((int) x, (int) y, (int) this.surface.getWidth(), (int) this.surface.getHeight());

        int j = 0;
        d.setColor(this.getRectangleColor(j));
        for (Rectangle r: this.rectangle) {
            Point upleft = r.getUpperLeft();
            d.setColor(this.getRectangleColor(j));
            d.fillRectangle((int) upleft.getX(), (int) upleft.getY(), (int) r.getWidth(), (int) r.getHeight());
            j++;
        }

        int red = 46;
        int green = 42;
        int blue = 41;
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
        int blue = 225;
        if (index == 1) {
            red = 246;
            green = 77;
            blue = 54;
        }
        if (index == 0) {
            red = 221;
            green = 157;
            blue = 94;
        }
        return new Color(red, green, blue);
    }
    /**
     * @param index rectangle creation number.
     * @return the correct color for the rectangle.
     */
    private Color getRectangleColor(int index) {
        int red = 78;
        int green = 74;
        int blue = 73;
        if (index == 0) {
            return Color.WHITE;
        }
        if (index == 1) {
            red = 62;
            green = 58;
            blue = 57;
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