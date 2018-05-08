package collisiondetections;

import geometryprimitives.Point;

/**
 * @author joffeda.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;
    /**
     * constructor.
     * @param collisionPoint1 the point where the collision occur.
     * @param obj the object with which the collision occur.
     */
    public CollisionInfo(Point collisionPoint1, Collidable obj) {
        this.collisionPoint = collisionPoint1;
        this.collisionObject = obj;
    }
    /**
     * @return the point where the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
