package sprites;
import collisiondetections.Collidable;
import collisiondetections.CollisionInfo;
import biuoop.DrawSurface;
import geometryprimitives.Line;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import characteristics.Velocity;
import characteristics.ColorInfo;
import screensandanimations.GameLevel;
import gamerelated.GameEnvironment;
import java.awt.Color;

/**
 * @author joffeda.
 */
public class Ball implements Sprite {
    private ColorInfo color;
    private Point center;
    private int radius;
    private Velocity velocity;
    private GameEnvironment gameEnviroment;
    private int speed;
    private boolean firtSpeedInitialize;

    /**
     * constructor.
     * @param center the center of the ball.
     * @param r      the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.basicConstructor(color, r);
    }

    /**
     * constructor.
     *
     * @param x     the x value of the center of the ball.
     * @param y     the y value of the center of the ball.
     * @param r     the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.basicConstructor(color, r);
    }

    /**
     * all the constructors use this.
     * @param color1 the ball's color.
     * @param r the ball's radius.
     */
    public void basicConstructor(Color color1, int r) {
        this.color = new ColorInfo();
        this.color.set(color1);
        this.setFrameColor(Color.BLACK);
        this.radius = r;
        this.velocity = new Velocity(0, 0);
        this.gameEnviroment = null;
        this.firtSpeedInitialize = true;
    }

    /**
     * accessor.
     *
     * @return the x value of the center of the ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * accessor.
     *
     * @return the y value of the center of the ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * accessor.
     *
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * accessor.
     *
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color.get();
    }

    /**
     * sets the ball's color.
     * @param color1 a color.
     */
    public void setColor(java.awt.Color color1) {
        this.color.set(color1);
    }

    /**
     * sets the ball's frame color.
     * @param color1 a color.
     */
    public void setFrameColor(java.awt.Color color1) {
        this.color.setFrameColor(color1);
    }

    /**
     * accessor.
     *
     * @return the velocity of the ball;
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param d a drawing surface.
     */
    public void drawOn(DrawSurface d) {
        if (this.color.get() != null) {
            d.setColor(this.color.get());
            d.fillCircle(this.getX(), this.getY(), this.radius);
        }
        d.setColor(this.color.getFrameColor());
        d.drawCircle(this.getX(), this.getY(), this.radius);
    }

    /**
     * set the velocity of the ball.
     * @param v a drawing surface.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
        if (this.firtSpeedInitialize) {
            this.speed = (int) v.getVectorSize();
            this.firtSpeedInitialize = false;
        }
    }


    /**
     * set the velocity of the ball.
     * @param dx the the x element of the direction vector.
     * @param dy the the y element of the direction vector.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
        if (this.firtSpeedInitialize) {
            this.speed = (int) this.velocity.getVectorSize();
            this.firtSpeedInitialize = false;
        }
    }

    /**
     * sets the ball's game enviroment.
     *
     * @param env a game enviroment.
     */
    public void setGameEnvironment(GameEnvironment env) {
        this.gameEnviroment = env;
    }


    /**
     * move the ball in the direction of it's velocity, and with
     * int the limits of it's game enviroment.
     */
    public void moveOneStep() {
        Point toBe = this.getVelocity().applyToPoint(this.center);
        Line trajectory = new Line(this.center, toBe);
        CollisionInfo colInfo =
                this.gameEnviroment.getClosestCollision(trajectory);
        if (colInfo == null) {
            // no collisions
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            Point collisionP = colInfo.collisionPoint();
            Collidable obstacle = colInfo.collisionObject();
            double x = this.center.getX();
            double y = this.center.getY();
            Rectangle rect = obstacle.getCollisionRectangle();
            double dx = this.getVelocity().dx();
            double dy = this.getVelocity().dy();

            if (collisionP.getY() == rect.upBorder()) { //upBorder = floor
                if (dx > 0) { // comming from the left.
                    x = collisionP.getX() - 0.00001;
                } else if (dx < 0) { // comming from the right.
                    x = collisionP.getX() + 0.00001;
                }
                if (dy > 0) { // comming from above.
                    y = collisionP.getY() - 0.00001;
                } else if (dy < 0) { //comming from the bottom.
                    y = collisionP.getY() - 0.000001;
                }
            }
            if (collisionP.getY() == rect.bottomBorder()) { //bottomBorder = ceiling
                if (dx > 0) { // comming from the left.
                    x = collisionP.getX() - 0.00001;
                } else if (dx < 0) { //// comming from the right.
                    x = collisionP.getX() + 0.00001;
                }
                if (dy < 0) { // comming from the bottom.
                    y = collisionP.getY() + 0.00001;
                } else if (dy > 0) { //comming from above.
                    y = collisionP.getY() + 0.000001;
                }
            }
            if (collisionP.getX() == rect.lBorder()) { ///lBorder = right
                if (dx > 0) {
                    x = collisionP.getX() - 0.000001;
                } else if (dx < 0) {
                    x = collisionP.getX() - 0.00001;
                }
                if (dy > 0) { // comming from above.
                    y = collisionP.getY() - 0.000001;
                } else if (dy < 0) { //comming from the bottom.
                    y = collisionP.getY() + 0.000001;
                }
            }
            if (collisionP.getX() == rect.rBorder()) { //rBorder = left
                if (dx < 0) {
                    x = collisionP.getX() + 0.00001;
                } else if (dx > 0) {
                    x = collisionP.getX() + 0.000001;
                }
                if (dy > 0) { // comming from above.
                    y = collisionP.getY() - 0.00001;
                } else if (dy < 0) { //comming from the bottom.
                    y = collisionP.getY() + 0.00001;
                }
            }
            this.center = new Point(x, y);
            Velocity newVelo = obstacle.hit(this, collisionP, this.velocity);
            this.setVelocity(newVelo);
        }
    }
    /**
     * tell the ball time has passed and it is time to move one step.
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        Velocity v = Velocity.fromAngleAndSpeed(this.getVelocity().getAngle(),
                this.speed * dt);
        this.velocity = v;



        this.moveOneStep();
    }
    /**
     * add the ball to the sprites list of the game.
     * @param game an Arkanoid game.
     */
    public void addToGame(GameLevel game) {
        game.getSprites().addSprite(this);
    }

    /**
     * remove the ball from the sprites list of the game.
     * @param game an Arkanoid game.
     */
    public void removeFromGame(GameLevel game) {
        game.getSprites().removeSprite(this);
    }
}