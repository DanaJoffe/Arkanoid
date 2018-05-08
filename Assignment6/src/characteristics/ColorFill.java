package characteristics;
import java.awt.Color;
import biuoop.DrawSurface;
import geometryprimitives.Rectangle;
import geometryprimitives.Point;

/**
 * @author joffeda.
 */
public class ColorFill implements Fill {
    private Color color;
    private Rectangle canvas;

    /**
     * constructor.
     * @param c color.
     * @param k a rectangle - where to fill the color.
     */
    public ColorFill(Color c, Rectangle k) {
        this.color = c;
        this.canvas = k;
    }

    /**
     * constructor.
     * @param c color.
     * @param upperLeft of the rectangle - where to fill the color.
     * @param width of the rectangle - where to fill the color.
     * @param height of the rectangle - where to fill the color.
     */
    public ColorFill(Color c, Point upperLeft, double width, double height) {
        this.color = c;
        this.canvas = new Rectangle(upperLeft, width, height);
    }

    /**
     * canvas with color.
     * @param d a Drawing Surface.
     */
    public void fillIn(DrawSurface d) {
        d.setColor(this.color);
        if (this.canvas.getHeight() == 0) { //no specific canvas is defined. thus- fill the whole surface.
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        } else {
            d.fillRectangle((int) this.canvas.getUpperLeft().getX(),
                    (int) this.canvas.getUpperLeft().getY(), (int)
                            this.canvas.getWidth(), (int) this.canvas.getHeight());
        }
    }

    /**
     * @param canvas1 a rectangle.
     * @return a new Color fill with a given frame.
     */
    public Fill withNewCanvas(Rectangle canvas1) {
        return new ColorFill(this.color, canvas1);
    }
}