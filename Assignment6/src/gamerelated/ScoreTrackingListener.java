package gamerelated;
import characteristics.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * @author joffeda.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * constructor.
     * @param scoreCounter a score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit a block that is being hit.
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.isAbleToBeRemoved()) {
            this.currentScore.increase(5); //for hitting a block.
            if (beingHit.getCollisionAmount() == 0) { //for destroying a block.
                this.currentScore.increase(10);
            }
        }
    }
}