package screensandanimations;
import biuoop.DrawSurface;
import characteristics.Fill;
import characteristics.ImageFill;

/**
 * @author joffeda.
 */
public class PauseScreen implements Animation {
    private String endKey;
    private Fill background;

    /**
     * @param endKey a key which pressing on it will close the animation.
     * @param background a path of a background image.
     */
    public PauseScreen(String endKey, String background) {
        this.endKey = endKey;
        this.setBackground(background);
    }

    /**
     * turning the path in to a Fill object that can be shown.
     * @param backgroundPath a path of a background image.
     */
    public void setBackground(String backgroundPath) {
        this.background = new ImageFill(backgroundPath,
                new geometryprimitives.Rectangle(new geometryprimitives.Point(0, 0), 0, 0));
    }
    /**
     * drawing the PauseScreen.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.fillIn(d);
        }
        d.drawText(10, d.getHeight() / 9, "paused -- press " + this.endKey + " to continue", 32);
    }
    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return false; }
}