package sprites;
import characteristics.ColorInfo;
import characteristics.HitNotifier;
import characteristics.HitListener;
import characteristics.Velocity;
import characteristics.Fill;
import characteristics.ImageFill;
import characteristics.ColorFill;
import collisiondetections.Collidable;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import screensandanimations.GameLevel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author joffeda.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private int colisionAmount;
    private boolean canBeRemoved;
    private ColorInfo color;
    private List<HitListener> hitListeners;
    /**
     * constructor.
     * @param upperLeft the upperLeft point of the block.
     * @param width the width of the block.
     * @param height the height of the block.
     */
    public Block(Point upperLeft, double width, double height) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.color = new ColorInfo();
        this.hitListeners = new ArrayList<HitListener>();
        this.canBeRemoved = true;
    }

    /**
     *
     * @return the block's width.
     */
    public double getWidth() {
        return this.rect.getWidth();
    }

    /**
     *
     * @return the block's height.
     */
    public double getHeight() {
        return this.rect.getHeight();
    }

    /**
     *
     * @return the x value of the get upper left point of the block.
     */
    public double getX() {
        return this.rect.getUpperLeft().getX();
    }

    /**
     *
     * @return the y value of the get upper left point of the block.
     */
    public double getY() {
        return this.rect.getUpperLeft().getY();
    }

    /**
     *
     * @param op the number of hit points left.
     * @param c the color we want the block to have when it has op hit points left.
     */
    public void addFillOption(Integer op, Color c) {
        this.color.addFillOption(op, new ColorFill(c, this.rect));
    }

    /**
     *
     * @param op the number of hit points left.
     * @param imagePath the image we want the block to have when it has op hit points left.
     */
    public void addFillOption(Integer op, String imagePath) {
        this.color.addFillOption(op, new ImageFill(imagePath, this.rect));
    }

    /**
     *
     * @param op the number of hit points left.
     * @param fill the fill (either color or image Fill) we want the block
     * to have when it has op hit points left.
     */
    public void addFillOption(Integer op, Fill fill) {
        this.color.addFillOption(op, fill.withNewCanvas(this.rect));
    }

    /**
     * @param ability true if the block can be removed by a ball, and false otherwise.
     */
    public void setRemovedAbility(boolean ability) {
        this.canBeRemoved = ability;
    }

    /**
     * parallel to: getRemovedAbility.
     * @return true if the block can be removed by a ball, and false otherwise.
     */
    public boolean isAbleToBeRemoved() {
        return this.canBeRemoved;
    }
    /**
     * constructor.
     * @param rectangle a rectangle that is the skeleton of the block.
     */
    public Block(Rectangle rectangle) {
        this.rect = rectangle;
        this.colisionAmount = 1;
        //this.colisionVelo = null;
        this.color = new ColorInfo();
        this.color.set(Color.BLACK);
        this.color.setFrameColor(Color.BLACK);
        this.hitListeners = new ArrayList<HitListener>();
        this.canBeRemoved = true;
    }

    /**
     *@return a rectangle that is the skeleton of the block, which sprites
     * can collide with.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * the method notifies the object that a ball collided with it at
     * collisionPoint with a given velocity.
     *@return the new velocity expected after the hit (based on
     *the force the object inflicted on us).
     *@param currentVelocity is the velocity which the ball has at the
     * collision time.
     *@param collisionPoint is the point of the collision.
     *@param hitter the Ball that's doing the hitting.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.dx();
        double dy = currentVelocity.dy();
        if (collisionPoint.getX() == this.rect.lBorder()
                || collisionPoint.getX() == this.rect.rBorder()) {
            dx *= -1;
        }
        if (collisionPoint.getY() == this.rect.bottomBorder()
                || collisionPoint.getY() == this.rect.upBorder()) {
            dy *= -1;
        }
        if (this.colisionAmount > 0) {
            this.colisionAmount -= 1;
        }
        if (!this.color.isPerminent()) { //change color when you have a hit.
            if (this.colisionAmount == 0) {
                this.color.set(Color.BLACK);
            } else {
                this.color.set(this.color.randomColor());
            }
        }
        this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }

    /**
     * the method draws the block on the given surface.
     * PRINTS A WARNING if there is no way of drawing this block.
     *@param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        Map<Integer, Fill> options = this.color.getFillOptions();
        if (!options.isEmpty() && options.containsKey(this.colisionAmount)) {
            options.get(this.colisionAmount).fillIn(d);
        } else if (options.containsKey(-1)) { //default color will be mapped by key -1.
            options.get(-1).fillIn(d);
        }
        if (this.color.getFrameColor() != null) {
            d.setColor(this.color.getFrameColor());
            d.drawRectangle((int) this.rect.getUpperLeft().getX(),
                    (int) this.rect.getUpperLeft().getY(), (int)
                            this.rect.getWidth(), (int) this.rect.getHeight());
        }
    }

    /**
     * tell the block time has passed and it is time to do something.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }

    /**
     * sets the ball's color.
     * @param color1 a color.
     */
    public void setColor(Color color1) { //set default color.
        this.color.addFillOption(-1, new ColorFill(color1, this.rect));
        //this.color.set(color1);
    }

    /**
     *@return the paint object that contains all the color info.
     */
    public ColorInfo checkColor() {
        return this.color;
    }

    /**
     * sets the block's frame color.
     * @param color1 a color.
     */
    public void setFrameColor(Color color1) {
        this.color.setFrameColor(color1);
    }

    /**
     * accessor.
     * @return the color of the block.
     */
    public Fill getColor() {
        return this.color.getFillOptions().get(-1);
        //return this.color.get();
    }

    /**
     * add the block to the sprites and collidables lists of the game.
     * @param game an Arkanoid game.
     */
    public void addToGame(GameLevel game) {
        game.getEnvironment().addCollidable(this);
        game.getSprites().addSprite(this);
    }

    /**
     * remove the block from the sprites and collidables lists of the game.
     * @param game an Arkanoid game.
     */
    public void removeFromGame(GameLevel game) {
        game.getEnvironment().removeCollidable(this);
        game.getSprites().removeSprite(this);
    }

    /**
     * The method writes how many collisions remain to the block.
     * @param d a draw surface.
     */
    public void writeHitNumber(DrawSurface d) {
        Rectangle rec = this.rect;
        int fontSize = (int) (Math.min(rec.getWidth(), rec.getHeight()) / 2);
        int y = (int) (rec.bottomBorder() - ((rec.getHeight() - fontSize) / 2));
        int x = (int) (rec.lBorder() + ((rec.getWidth() - fontSize) / 2));

        if (this.colisionAmount == 0) {
            d.setColor(Color.WHITE);
            d.drawText(x, y, "X", fontSize);
        }
        d.setColor(Color.BLACK);
        if (this.colisionAmount == 1) {
            d.drawText(x, y, "1", fontSize);
        }
        if (this.colisionAmount == 2) {
            d.drawText(x, y, "2", fontSize);
        }
    }

    /**
     * the method sets how many collisions can the block take.
     * @param amount a collision limit for the block.
     */
    public void setColisionAmount(int amount) {
        this.colisionAmount = amount;
    }

    /**
     * @return how many times this block was collide with.
     */
    public int getCollisionAmount() {
        return this.colisionAmount;
    }

    /**
     * Add hl as a listener to hit events.
     * @param hl a listener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl a listener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notify all the listeners that there was a hit.
     * @param hitter the ball that hit this block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}