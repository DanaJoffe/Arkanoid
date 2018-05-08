package gamerelated;
import screensandanimations.GameLevel;
import sprites.Paddle;
/**
 * @author joffeda.
 */
public class PaddleRemover {
    private Paddle paddle;

    /**
     * contructor.
     * @param p a paddle to spy on.
     */
    public PaddleRemover(Paddle p) {
        this.paddle = p;
    }

    /**
     * @param game a game level from which to remove the paddle.
     */
    public void remove(GameLevel game) {
        this.paddle.removeFromGame(game);
    }
}
