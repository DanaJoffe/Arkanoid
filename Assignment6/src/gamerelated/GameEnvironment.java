package gamerelated;
import collisiondetections.Collidable;
import collisiondetections.CollisionInfo;
import geometryprimitives.Line;
import geometryprimitives.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * constructor.
     * make a new collidables list.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * add the given collidable to the environment.
     * @param c a collidable object.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * remove the given collidable from the environment.
     * @param c a collidable object.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory a Line object, from a ball's center to it's
     * next point of progress.
     * @return the collision info = an object contains the closest
     * cllidable and the closest collision point with it.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Collidable> allCollidables = new ArrayList<Collidable>(this.collidables);
        if (allCollidables.size() == 0) { //there are no collidables.
            return null;
        }
        Collidable closestCollidable = null;
        double minDist = 0, dist;
        Point interPoint, closestCollisionPoint = null;
        int checker = 0;
        for (int i = 0; i < allCollidables.size(); i++) { // go over the collidables.
            interPoint = trajectory.closestIntersectionToStartOfLine(allCollidables.
                    get(i).getCollisionRectangle());
            if (interPoint != null) { // there is an intersection between collidable and trajectory.
                dist = interPoint.distance(trajectory.start());
                if (checker == 0) { //first collidable that collides.
                    closestCollidable = allCollidables.get(i);
                    closestCollisionPoint = interPoint;
                    minDist = dist; //distance from start().
                    checker += 1;
                } else if (dist < minDist) { //not the first collidable that collides.
                        minDist = dist;
                        closestCollidable = allCollidables.get(i);
                        closestCollisionPoint = interPoint;
                }
            }
        }
        if (closestCollidable  == null) { // there is no collision.
            return null;
        }
        return new CollisionInfo(closestCollisionPoint, closestCollidable);
    }
}
