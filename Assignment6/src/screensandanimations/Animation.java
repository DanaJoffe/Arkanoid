package screensandanimations;
import biuoop.DrawSurface;

/**
 * @author joffeda.
 */
public interface Animation {
    /**
     * drawing one frame of the animation.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return true if the animation should stop, and false otherwise.
     */
    boolean shouldStop();
}
