package characteristics;
import sprites.Block;
import sprites.Ball;
/**
 * @author joffeda.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit a block that is being hit.
     * @param hitter the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}