package levelsinfo;
import characteristics.Velocity;
import sprites.Block;
import sprites.Sprite;
import java.util.List;

/**
 * @author joffeda.
 */
public interface LevelInformation {
    /**
     * @return how many balls are in the level.
     */
    int numberOfBalls();
    /**
     * @return an array with The initial velocity of each ball.
     */
    List<Velocity> initialBallVelocities();
    /**
     * @return the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * @return the width of the paddle.
     */
    int paddleWidth();
    /**
     * @return the level name that will be displayed at the top of the screen.
     */
    String levelName();

    /**
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     *
     * @return an array with The Blocks that make up this level, each block contains
     * its size, color and location.
     */
    List<Block> blocks();
    /**
     * @return the number of blocks that should be removed in order to consider this level to be "cleared".
     */
    int numberOfBlocksToRemove();
}