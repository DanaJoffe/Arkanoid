package gamerelated;
import characteristics.HitListener;
import screensandanimations.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * @author joffeda.
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 *  of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor.
     * @param game1 a game level.
     * @param removedBlocks a counter the starts with the initial number of blocks in the level.
     */
    public BlockRemover(GameLevel game1, Counter removedBlocks) {
        this.game = game1;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points are being removed
     * from the game.
     * @param beingHit a block that is being hit.
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getCollisionAmount() == 0 && beingHit.isAbleToBeRemoved()) {
            this.remainingBlocks.decrease(1);
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
        }
    }
}