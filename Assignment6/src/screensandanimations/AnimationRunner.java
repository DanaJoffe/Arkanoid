package screensandanimations;
import biuoop.GUI;
import biuoop.DrawSurface;

/**
 * @author joffeda.
*/
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;

    /**
     * constructor.
     * @param gui1 a gui.
     * @param rate the rate of the animation.
     * @param sleeper1 a sleeper.
     */
    public AnimationRunner(GUI gui1, int rate, biuoop.Sleeper sleeper1) {
        this.gui = gui1;
        this.framesPerSecond = rate;
        this.sleeper = sleeper1;
    }

    /**
     * runs the animation in a loop.
     * @param animation an animation.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, (1 / this.framesPerSecond));
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     *
     * @return the rate of the animation.
     */
    public int getRate() {
        return this.framesPerSecond;
    }
}