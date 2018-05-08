package characteristics;

import biuoop.DrawSurface;
import geometryprimitives.Rectangle;

/**
 * @author joffeda.
 */
public interface Fill {
    /**
     * the Fill object will draw itself on a given d.
     * @param d a drawing surface.
     */
    void fillIn(DrawSurface d);

    /**
     * @param canvas a rectangle - where to fill the fill.
     * @return the same fill value but with a different fill' location.
     */
    Fill withNewCanvas(Rectangle canvas);
}
