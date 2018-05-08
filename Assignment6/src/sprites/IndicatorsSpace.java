package sprites;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import screensandanimations.GameLevel;
import java.awt.Color;

/**
 * @author joffeda.
 */
public class IndicatorsSpace implements Sprite {
    private Rectangle rectangle;
    private Color color;

    /**
     * constructor.
     * @param upperleft the upperleft point of the rectangle in which the indicators are shown.
     * @param wid the width of the rectangle in which the indicators are shown.
     * @param height the height of the rectangle in which the indicators are shown.
     * @param c the color of the rectangle in which the indicators are shown.
     */
    public IndicatorsSpace(Point upperleft, int wid, int height, Color c) {
        this.rectangle = new Rectangle(upperleft, wid, height);
        this.color = c;
    }
    /**
     * draw the sprite to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }
    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        int x;
    }
    /**
     * add the LevelNameIndicator to the sprites list of the game.
     * @param game an Arkanoid game.
     */
    public void addToGame(GameLevel game) {
        game.getSprites().addSprite(this);
    }
}