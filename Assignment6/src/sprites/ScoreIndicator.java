package sprites;
import biuoop.DrawSurface;
import gamerelated.Counter;
import screensandanimations.GameLevel;
import geometryprimitives.Point;
import java.awt.Color;

/**
* @author joffeda.
*/
public class ScoreIndicator implements Sprite {
    private Counter score;
    private int fontSize;
    private Point upperLeft;

    /**
     * constructor.
     * @param score1 score counter.
     * @param fontSize1 font size.
     * @param middle a point on the screen- where to write the score.
     */
    public ScoreIndicator(Counter score1, int fontSize1, Point middle) {
        this.score = score1;
        this.fontSize = fontSize1;
        this.upperLeft = middle;
    }
    /**
     * draws the score.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.upperLeft.getX();
        int y = (int) this.upperLeft.getY();
        String write = "Score: ".concat(this.score.toString());
        d.setColor(Color.BLACK);
        d.drawText(x, y, write, this.fontSize);
    }
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
    /**
     * add the LevelNameIndicator to the sprites list of the game.
     * @param game an Arkanoid game.
     */
    public void addToGame(GameLevel game) {
        game.getSprites().addSprite(this);
    }
}
