package sprites;
import screensandanimations.GameLevel;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import characteristics.Velocity;
import characteristics.ColorInfo;
import characteristics.Section;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import collisiondetections.Collidable;

/**
 * @author joffeda.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Rectangle gameSpace;
    private ColorInfo color;
    private int speed;
    private int pixelsPerSec;

//    public Paddle(GameBoard board, biuoop.KeyboardSensor key) {
//        this.keyboard = key;
//        this.gameSpace = board.getGameSpace();
//        this.setSkeleton();
//        this.color = new ColorInfo();
//        this.color.set(Color.ORANGE);
//        this.setSpeed((int) (this.gameSpace.getWidth() / 100));
//    }

    /**
     * constructor.
     * @param space the rectangle in which the paddle can move.
     * @param pixelsPerSec the paddle's speed.
     * @param width the paddle's width.
     * @param key a keyboard sensor to move the paddle.
     */
    public Paddle(Rectangle space, int pixelsPerSec, int width, biuoop.KeyboardSensor key) {
        this.keyboard = key;
        this.gameSpace = space;
        this.setSkeleton(width);
        this.color = new ColorInfo();
        this.color.set(Color.ORANGE);
        this.color.setFrameColor(Color.BLACK);
        this.setSpeed(pixelsPerSec);
    }

    /**
     * set the frame of the paddle.
     * @param width the width of the paddle.
     */
    private void setSkeleton(int width) {
        int paddleWidth = width;
        int paddleHeight = (int) (gameSpace.getHeight() / 34);
        int y = (int) (gameSpace.bottomBorder() - (1.5 * paddleHeight));
        int x = (int) (gameSpace.rBorder() - (gameSpace.getWidth() / 2)
                - (paddleWidth / 2));
        Point upperLeft = new Point(x, y);
        this.rect = new Rectangle(upperLeft, paddleWidth, paddleHeight);
    }

    /**
     * the method moves paddle to the left.
     */
    public void moveLeft() {
        int x = this.speed;
        Point movement = this.rect.getUpperLeft();
        if (movement.getX() - x <= this.gameSpace.lBorder()) {
            int gap = (int) (movement.getX() - this.gameSpace.lBorder());
            movement = new Point(movement.getX() - gap, movement.getY());
            this.rect = new Rectangle(movement, this.rect.getWidth(),
                    this.rect.getHeight());
        } else {
            movement = new Point(movement.getX() - x, movement.getY());
            this.rect = new Rectangle(movement, this.rect.getWidth(), this.rect.getHeight());
        }
    }
    /**
     * the method moves paddle to the right.
     */
    public void moveRight() {
        int x = this.speed;
        Point movement = this.rect.getUpperLeft();
        if (movement.getX() + this.rect.getWidth() + x >= this.gameSpace.rBorder()) {
            int gap = (int) (this.gameSpace.rBorder() - (movement.getX() + this.rect.getWidth()));
            movement = new Point(movement.getX() + gap, movement.getY());
            this.rect = new Rectangle(movement, this.rect.getWidth(),
                    this.rect.getHeight());
        } else {
            movement = new Point(movement.getX() + x, movement.getY());
            this.rect = new Rectangle(movement, this.rect.getWidth(), this.rect.getHeight());
        }
    }
    /**
     * the method sets the paddle's speed.
     * @param x the new speed;
     */
    public void setSpeed(int x) {
        this.pixelsPerSec = x;
        this.speed = x;
    }

    /**
     * @return the color of the paddle;
     */
    public Color getColor() {
        return this.color.get();
    }

    /**
     * Sprite interface method.
     * notify the paddle that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.speed = (int) (this.pixelsPerSec * dt);




        if (keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * Sprite interface method.
     * draw the paddle to the given surface.
     * @param d a surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        if (this.getColor() != null) {
            d.setColor(this.getColor());
        }
        Rectangle rectan = this.rect;
        d.fillRectangle((int) rectan.getUpperLeft().getX(),
                (int) rectan.getUpperLeft().getY(), (int) rectan.getWidth(),
                (int) rectan.getHeight());
        d.setColor(this.color.getFrameColor());
        d.drawRectangle((int) rectan.getUpperLeft().getX(),
                (int) rectan.getUpperLeft().getY(), (int) rectan.getWidth(),
                (int) rectan.getHeight()); //black borders
    }

    /**
     * Collidable interface method.
     *@return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }
    /**
     * regular hit.
     * Collidable interface method.
     * the method notifies the object that a ball collided with it at
     * collisionPoint with a given velocity.
     *@return the new velocity expected after the hit (based on
     *the force the object inflicted on us).
     *@param currentVelocity is the velocity which the ball has at the
     * collision time.
     *@param collisionPoint is the point of the collision.
     */
    public Velocity oldHit(Point collisionPoint, Velocity currentVelocity) {
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
        if (!this.checkColor().isPerminent()) {
            this.color.set(this.color.randomColor());
        }
        return new Velocity(dx, dy);
    }
    /**
     * Fun Hit.
     * Collidable's interface method.
     * the method notifies the paddle that a ball collided with it at
     * collisionPoint with a given velocity.
     *@return the new velocity expected after the hit (based on
     *where the object collided with us).
     *@param currentVelocity is the velocity which the ball has at the
     * collision time.
     *@param collisionPoint is the point of the collision.
     *@param hitter the Ball that's doing the hitting.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        List<Section> sections = this.partitionToSections(5);
        double ballSpeed = currentVelocity.getVectorSize();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).isInSection((int) collisionPoint.getX())) {
                if (i == 0) {
                    return Velocity.fromAngleAndSpeed(300, ballSpeed);
                }
                if (i == 1) {
                    return Velocity.fromAngleAndSpeed(330, ballSpeed);
                }
                if (i == 2) {
                    return this.oldHit(collisionPoint, currentVelocity);
                }
                if (i == 3) {
                    return Velocity.fromAngleAndSpeed(30, ballSpeed);
                }
                if (i == 4) {
                    return Velocity.fromAngleAndSpeed(60, ballSpeed);
                }
            }

        }
        return this.oldHit(collisionPoint, currentVelocity);
    }
    /**
     * the method devides the hit surface into segments, and each segment
     * will act different when being hit.
     * @param sectionsAmount a number indicates to how many parts should
     * the paddle's hit surface be divided
     * collision time.
     * @return a list containing the different hit sections on the paddle.
     */
    public List<Section> partitionToSections(int sectionsAmount) {
        List<Section> sections = new ArrayList<Section>();
        int right = (int) this.rect.rBorder();
        int left = (int) this.rect.lBorder();
        int sectionWidth = this.getWidth() / sectionsAmount;
        int x1 = left;
        int x2 = x1 + sectionWidth;
        for (int i = 0; i < sectionsAmount; i++) {
            sections.add(new Section(x1, x2));
            x1 = x2 + 1;
            if (i == sectionsAmount - 1) {
                x2 = right;
            } else {
                x2 = x1 + sectionWidth - 1;
            }
        }
        return sections;
    }

    /**
     * @return the width of the paddle.
     */
    public int getWidth() {
        return (int) this.rect.getWidth();
    }

    /**
     * add the block to the sprites and collidables lists of the game.
     * @param g an Arkanoid game.
     */
    public void addToGame(GameLevel g) {
        g.getEnvironment().addCollidable(this);
        g.getSprites().addSprite(this);
    }

    /**
     * remove the paddle from the sprites list of the game.
     * @param game an Arkanoid game.
     */
    public void removeFromGame(GameLevel game) {
        game.getEnvironment().removeCollidable(this);
        game.getSprites().removeSprite(this);
    }

    /**
     * @return the whole color information of the paddle.
     */
    public ColorInfo checkColor() {
        return this.color;
    }

    /**
     * a tester for this class.
     *@param args ignored.
     */
    public static void main(String[] args) {
        int sectionsAmount = 5;
        List<Section> sections = new ArrayList<Section>();
        int right = 230; //(int) this.rect.rBorder();
        int left = 200; //(int) this.rect.lBorder();
        int sectionWidth = 30 / sectionsAmount; //this.getWidth() / 5;

        int x1 = left;
        int x2 = x1 + sectionWidth;

        for (int i = 0; i < sectionsAmount; i++) {
            sections.add(new Section(x1, x2));
            x1 = x2 + 1;
            if (i == sectionsAmount - 1) {
                x2 = right;
            } else {
                x2 = x1 + sectionWidth - 1;
            }
        }
    }
}