package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class SpriteCollection {
    private List<Sprite> sprites;
    /**
     * constructor.
     * make a new sprites list.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**
     * add the given sprite to the sprites array.
     * @param s a Sprite object.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    /**
     * remove the given sprite from the sprites array.
     * @param s a Sprite object.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * call timePassed() on all sprites..
     * @param dt the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> allSprites = new ArrayList<Sprite>(this.sprites);
        for (int i = 0; i < allSprites.size(); i++) { // go over the Sprites.
            allSprites.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites..
     * @param d a surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> allSprites = new ArrayList<Sprite>(this.sprites);
        for (int i = 0; i < allSprites.size(); i++) { // go over the Sprites.
            allSprites.get(i).drawOn(d);
        }
    }
}