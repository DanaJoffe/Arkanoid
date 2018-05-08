package redundants;

import characteristics.Velocity;
import geometryprimitives.Point;
import levelsinfo.LevelInformation;
import sprites.Block;
import sprites.Sprite;
import geometryprimitives.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class DirectHit implements LevelInformation {
    private Rectangle gameSpace;
    private List<Block> blocks;

    /**
     * constructor.
     * @param width the width of the game window.
     * @param height the height of the game window.
     * @param bordersThick the game's borders' thick.
     */
    public DirectHit(int width, int height, int bordersThick) {
        this.gameSpace = new Rectangle(new Point(bordersThick, 2 * bordersThick),
                width - (2 * bordersThick), height - (2 * bordersThick));
        this.setBlocks();
    }
    /**
     * @return how many balls are in the level.
     */
    public int numberOfBalls() {
        return this.initialBallVelocities().size();
    }
    /**
     * @return an array with The initial velocity of each ball.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<Velocity>();
        velocities.add(Velocity.fromAngleAndSpeed(0, 300));
        return velocities;
    }
    /**
     * @return the speed of the paddle.
     */
    public int paddleSpeed() {
        int speed = 400;//(int) (this.getGameSpace().getWidth() / 100); //relatively high speed
        return speed;
    }
    /**
     * @return the width of the paddle.
     */
    public int paddleWidth() {
        int paddleWidth = (int) (this.getGameSpace().getWidth() / 9);
        return paddleWidth;
    }
    /**
     * @return the level name that will be displayed at the top of the screen.
     */
    public String levelName() {
        return "Direct Hit";
    }
    /**
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        return new DirectHitBackground(this.getGameSpace());
    }

    /**
     *
     * @return an array with The Blocks that make up this level, each block contains
     * its size, color and location.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * sets all the blocks of the level.
     */
    public void setBlocks() {
        this.blocks = new ArrayList<Block>();
        int obWidth = (int) (this.getGameSpace().getWidth() / 25);
        int obHeight = obWidth;
        int x = (int) (this.getGameSpace().getUpperLeft().getX());
        int y = (int) (this.getGameSpace().getUpperLeft().getY());

        Point upperLeft = new Point(x + (this.getGameSpace().getWidth() / 2) - (obWidth / 2),
                y + (3.5 * obHeight));
        Block block = new Block(upperLeft, obWidth, obHeight);
        block.setColor(Color.RED);
        block.setColisionAmount(1);
        this.blocks.add(block);
    }

    /**
     * @return the number of blocks that should be removed in order to consider this level to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
    /**
     * @return the rectangle in which the level is taking place.
     */
    public Rectangle getGameSpace() {
        return this.gameSpace;
    }
}
