package gamerelated;
import characteristics.HitListener;
import screensandanimations.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * @author joffeda.
 *  a BallRemover is in charge of removing balls from the game, as well as keeping count
 *  of the number of balls that remain. it also notifies paddleRemover whene needed.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;
    private PaddleRemover paddleRemover;

    /**
     * constructor.
     * @param game1 a game level.
     * @param remainingBalls1 a counter the starts with the initial number of balls in the level.
     * @param padRem a paddleRemover.
     */
    public BallRemover(GameLevel game1, Counter remainingBalls1, PaddleRemover padRem) {
        this.game = game1;
        this.remainingBalls = remainingBalls1;
        this.paddleRemover = padRem;
    }

    /**
     * the method takes out the ball from the current game.
     * if there are no balls, the method takes out also the paddle.
     * @param beingHit a block that is being hit.
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
        if (this.remainingBalls.getValue() == 0) {
            beingHit.removeHitListener(this);
            this.paddleRemover.remove(this.game);
        }
    }
}