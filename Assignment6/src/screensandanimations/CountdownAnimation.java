package screensandanimations;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * @author joffeda.
 */
// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private int callingAmount;

    /**
     *
     * @param numOfSeconds how many seconds lasts the countdown.
     * @param countFrom the number to start the count from.
     * @param gameScreen the screen on which the countdown will be displayed on.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds * 60 / countFrom;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;

        this.stop = false;
        this.callingAmount = 0;
    }
    /**
     * drawing one frame of the animation.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.callingAmount++;
        if (this.callingAmount % numOfSeconds == 0) {
            this.countFrom -= 1;
        }
        this.gameScreen.drawAllOn(d);
//        if (this.countFrom == 0) {
//            d.setColor(Color.WHITE);
//            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "GO!", 77);
//            d.setColor(Color.BLACK);
//            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "GO!", 75);
//            this.gameScreen.notifyAllTimePassed());
//        } else
            if (this.countFrom <= 0) {
            this.stop = true;
            //this.gameScreen.notifyAllTimePassed();
        } else {
            d.setColor(Color.WHITE);
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(this.countFrom), 77);
            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(this.countFrom), 75);
        }
    }
    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}