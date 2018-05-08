package screensandanimations;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

/**
 * @author joffeda.
 */
public class KeyPressStoppableAnimation implements Animation {
    private Animation animation;
    private KeyboardSensor keyboard;
    private String endKey;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * constructor.
     * @param sensor a KeyboardSensor .
     * @param key a string key that when it's being pressed- the animation stops.
     * @param animation animation be stopped.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.animation = animation;
        this.keyboard = sensor;
        this.endKey = key;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * drawing one frame of the animation.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.keyboard.isPressed(this.endKey)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public  boolean shouldStop() {
        return (this.stop || this.animation.shouldStop());
    }
}
