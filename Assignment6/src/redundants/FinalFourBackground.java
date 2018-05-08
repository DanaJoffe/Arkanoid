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
public class FinalFourBackground implements Sprite {
    private Rectangle surface;
    private List<Line> lines;
    private List<Circle> circles;

    /**
     * constructor.
     * @param rect a rectangle that represents the boundaries of the background.
     */
    public FinalFourBackground(Rectangle rect) {
        this.surface = rect;
        this.setLines();
    }
    /**
     * @return the rectangle that represents the boundaries of the background.
     */
    private Rectangle getSurface() {
        return this.surface;
    }
    /**
     * the method sets the lines of the drawing.
     * this method calls setCircles.
     */
    private void setLines() {
        this.lines = new ArrayList<Line>();
        this.circles = new ArrayList<Circle>();

        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);
        int x = (int) (this.getSurface().getUpperLeft().getX());
        int y = (int) (this.getSurface().getUpperLeft().getY());

        double gap = obWidth / 5 + 1;

        //left rain.
        Point downleft = new Point(x + obWidth, y + this.getSurface().getHeight());
        Point upperleft = new Point(x + (1.5 * obWidth), downleft.getY() - (8 * obHeight));
        Point upperright = new Point(upperleft.getX() + (2 * obWidth), upperleft.getY());

        while (upperleft.getX() < upperright.getX()) {
            Line l = new Line(upperleft, downleft);
            this.lines.add(l);
            upperleft = new Point(upperleft.getX() + gap, upperleft.getY());
            downleft = new Point(downleft.getX() + gap, downleft.getY());
        }

        //right rain.
        downleft = new Point(x + this.getSurface().getWidth() - (4 * obWidth), downleft .getY());
        upperleft = new Point(downleft.getX() + (0.5 * obWidth), downleft.getY() - (3.5 * obHeight));
        upperright = new Point(upperleft.getX() + (2 * obWidth), upperleft.getY());

        setCircles(new Point(x + (1.5 * obWidth), downleft.getY() - (8 * obHeight)), upperleft);

        while (upperleft.getX() <= upperright.getX()) {
            Line l = new Line(upperleft, downleft);
            this.lines.add(l);
            upperleft = new Point(upperleft.getX() + gap, upperleft.getY());
            downleft = new Point(downleft.getX() + gap, downleft.getY());
        }
    }

    /**
     * the method sets the circles of the drawing.
     * this method is being called by setLines.
     * @param centerLeft point start of left rain.
     * @param centerRight point start of right rain.
     */
    private void setCircles(Point centerLeft, Point centerRight) {
        int obWidth = (int) (this.getSurface().getWidth() / 15);
        int obHeight = (int) (this.getSurface().getHeight() / 22);

        double radius = 0.9 * obHeight;
        this.circles.add(new Circle(centerLeft, radius));
        this.circles.add(new Circle(centerRight, radius));

        radius = 1 * obHeight;
        centerLeft = new Point(centerLeft.getX() + 0.3 * obWidth, centerLeft.getY() + 0.75 * obHeight);
        centerRight = new Point(centerRight.getX() + 0.3 * obWidth, centerRight.getY() + 1.3 * obHeight);
        this.circles.add(new Circle(centerLeft, radius));
        this.circles.add(new Circle(centerRight, radius));

        radius = 1.1 * obHeight;
        centerLeft = new Point(centerLeft.getX() + 0.45 * obWidth, centerLeft.getY() - obHeight);
        centerRight = new Point(centerRight.getX() + 0.4 * obWidth, centerRight.getY() - 0.8 * obHeight);
        this.circles.add(new Circle(centerLeft, radius));
        this.circles.add(new Circle(centerRight, radius));

        radius = 0.75 * obHeight;
        centerLeft = new Point(centerLeft.getX() + 0.3 * obWidth, centerLeft.getY() + 1.25 * obHeight);
        centerRight = new Point(centerRight.getX() + 0.3 * obWidth, centerRight.getY() + 0.8 * obHeight);
        this.circles.add(new Circle(centerLeft, radius));
        this.circles.add(new Circle(centerRight, radius));

        radius = 1.3 * obHeight;
        centerLeft = new Point(centerLeft.getX() + 0.4 * obWidth, centerLeft.getY() - 0.9 * obHeight);
        centerRight = new Point(centerRight.getX() + 0.5 * obWidth, centerRight.getY() - 0.75 * obHeight);
        this.circles.add(new Circle(centerLeft, radius));
        this.circles.add(new Circle(centerRight, radius));
    }

    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        double x = this.surface.getUpperLeft().getX();
        double y = this.surface.getUpperLeft().getY();


        d.setColor(new Color(23, 136, 208));
        d.fillRectangle((int) x, (int) y, (int) this.surface.getWidth(), (int) this.surface.getHeight());

//        int j = 0;
//        d.setColor(this.getRectangleColor(j));
//        for (Rectangle r: this.rectangle) {
//            Point upleft = r.getUpperLeft();
//            d.setColor(this.getRectangleColor(j));
//            d.fillRectangle((int) upleft.getX(), (int) upleft.getY(), (int) r.getWidth(), (int) r.getHeight());
//            j++;
//        }

        d.setColor(Color.WHITE);
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
        //d.setColor(Color.BLACK);
        //d.fillCircle((int)x,(int)y,10);
    }

    /**
     * @param index circle creation number.
     * @return the correct color for the circle.
     */
    private Color getCircleColor(int index) {
        int red = 170;
        int green = 170;
        int blue = 170;
        if (index >= 0 && index <= 3) {
            red = 204;
            green = 204;
            blue = 204;
        }
        if (index >= 4 && index <= 5) {
            red = 187;
            green = 187;
            blue = 187;
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