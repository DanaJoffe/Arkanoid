package screensandanimations;
import biuoop.DrawSurface;
import characteristics.Fill;
import characteristics.ImageFill;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;

/**
 * @author joffeda.
 */
public class EndScreen implements Animation {
    private String endKey;
    private boolean win;
    private int score;
    private Fill winBackground;
    private Fill loseBackground;

    /**
     * constructor.
     * @param endKey a key which pressing on it will close the animation.
     * @param win true if the user won, false if he lost.
     * @param score the score at the end of the game.
     * @param winB a path of a background image for the win screen.
     * @param losB a path of a background image for the lose screen.
     */
    public EndScreen(String endKey, boolean win, int score, String winB, String losB) {
        this.endKey = endKey;
        this.win = win;
        this.score = score;
        this.loseBackground = new ImageFill(losB, new Rectangle(new Point(0, 0), 0, 0));
        this.winBackground = new ImageFill(winB, new Rectangle(new Point(0, 0), 0, 0));
    }
    /**
     * drawing the EndScreen - according to win member.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (win) {
            this.winBackground.fillIn(d);
            d.drawText(10, d.getHeight() / 2, "You Win! Your score is ".concat(Integer.toString(this.score)),
                    32);
        } else {
            this.loseBackground.fillIn(d);
            d.drawText(10, d.getHeight() / 2, "Game Over. Your score is ".concat(Integer.toString(this.score)),
                    32);
        }
        d.drawText(10, d.getHeight() / 2 + 64, "press " + this.endKey + " to exit", 32);
    }
    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return false; }
}