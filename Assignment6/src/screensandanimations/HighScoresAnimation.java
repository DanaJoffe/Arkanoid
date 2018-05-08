package screensandanimations;
import characteristics.Fill;
import characteristics.ImageFill;
import gamerelated.HighScoresTable;
import biuoop.DrawSurface;
import gamerelated.ScoreInfo;
import java.awt.Color;

/**
 * @author joffeda.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highscores;
    private String endKey;
    private Fill background;

    /**
     * all the constructors use this.
     * @param scores an HighScoresTable object to show.
     * @param endKey1 an exit key to show.
     */
    public void basicConstructor(HighScoresTable scores, String endKey1) {
        this.endKey = endKey1;
        this.highscores = scores;
        this.background = null;
    }
    /**
     * constructor.
     * @param scores an HighScoresTable object to show.
     * @param endKey an exit key to show.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey) {
        basicConstructor(scores, endKey);
    }

    /**
     constructor.
     * @param scores an HighScoresTable object to show.
     * @param endKey an exit key to show.
     * @param background a path of a background image.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, String background) {
        basicConstructor(scores, endKey);
        this.setBackground(background);
    }

    /**
     * turning the path to a Fill object that can be shown.
     * @param background1 a path of a background image.
     */
    public void setBackground(String background1) {
        this.background = new ImageFill(background1,
                new geometryprimitives.Rectangle(new geometryprimitives.Point(0, 0), 0, 0));
    }
    /**
     * drawing one frame of the animation.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.background != null) {
            this.background.fillIn(d);
        }
        d.setColor(Color.BLACK);

        int x = d.getHeight() / 12;
        int y = d.getWidth() / 15;
        d.drawText(x, y, "High Scores", 32);

        y = d.getHeight() / 5;
        int xName = d.getWidth() / 6;
        int xScore = d.getWidth() / 2;

        d.drawText(xName, y, "Player Name", 25);
        d.drawText(xScore, y, "Score", 25);
        int gap = d.getHeight() / 50;

        y += gap;
        d.drawLine(xName, y, (5 * xName), y);
        gap = d.getHeight() / 12;

        if (!this.highscores.getHighScores().isEmpty()) {
            for (ScoreInfo scoreInfo: this.highscores.getHighScores()) {
                y += gap;
                d.drawText(xName, y, scoreInfo.getName(), 23);
                d.drawText(xScore, y, Integer.toString(scoreInfo.getScore()), 23);
            }
        }
        d.drawText(d.getWidth() / 4 , (5 * d.getHeight() / 6), "Press " + this.endKey + " key to continue", 32);
    }

    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        return false;
    }
}