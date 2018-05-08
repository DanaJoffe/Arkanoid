package collisiondetections;
import characteristics.Velocity;
import sprites.Ball;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;

/**
 * author Dana Joffe.
 */
public interface Collidable {
    /**
     *@return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
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
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}