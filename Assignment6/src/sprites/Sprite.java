package sprites;

import biuoop.DrawSurface;

/**
 * @author joffeda.
 */
public interface Sprite {
    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}
