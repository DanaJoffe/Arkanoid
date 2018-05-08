package sprites;

import biuoop.DrawSurface;
import characteristics.Fill;

/**
 * @author joffeda.
 */
public class BasicBackground implements Sprite {
    private Fill fill;

    /**
     * constructor.
     * @param fill1 a Fill object. contains a color or an image.
     */
    public BasicBackground(Fill fill1) {
        this.fill = fill1;
    }
    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        this.fill.fillIn(d);
    }
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        int x;
    }
}