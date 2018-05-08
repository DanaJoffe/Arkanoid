package sprites;
import biuoop.DrawSurface;
import screensandanimations.GameLevel;
import geometryprimitives.Point;
import java.awt.Color;

/**
 * @author joffeda.
 */
public class LevelNameIndicator implements Sprite {
    private String levelName;
    private int fontSize;
    private Point upperLeft;

    /**
     * constructor.
     * @param levelName1 a level's name.
     * @param fontSize1 font size.
     * @param middle point on the screen- where to write the level's name.
     */
    public LevelNameIndicator(String levelName1, int fontSize1, Point middle) {
        this.levelName = levelName1;
        this.fontSize = fontSize1;
        this.upperLeft = middle;
    }
    /**
     * draw the name of the level.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.upperLeft.getX();
        int y = (int) this.upperLeft.getY();
        String write = "Level Name: ".concat(this.levelName);
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