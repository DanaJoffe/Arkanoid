package levelsinfo;

import characteristics.ColorFill;
import characteristics.Fill;
import characteristics.ImageFill;
import characteristics.Velocity;
import sprites.BasicBackground;
import sprites.Block;
import sprites.Sprite;
import java.awt.Color;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class BasicLevelInformation implements LevelInformation {
    private List<Block> blocks;
    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private int numberOfBlocksToRemove;
    private Sprite background;

    /**
     * constructor.
     */
    public BasicLevelInformation() {
        this.blocks = new ArrayList<Block>();
        this.velocities = new ArrayList<Velocity>();

    }

    /**
     *
     * @param velos velocities list.
     */
    public void setVelocities(List<Velocity> velos) {
        this.velocities.addAll(velos);
    }

    /**
     *
     * @param blocks1 blocks list.
     */
    public void setBlocks(List<Block> blocks1) {
        this.blocks.addAll(blocks1);
    }

    /**
     *
     * @param nobtRemove the Number Of Blocks that you need To Remove in order to win.
     */
    public void setNumberOfBlocksToRemove(int nobtRemove) {
        this.numberOfBlocksToRemove = nobtRemove;
    }

    /**
     *
     * @param c background color.
     */
    public void setBackground(Color c) {
        Fill fill = new ColorFill(c,
                new Rectangle(new Point(0, 0), 0, 0));
        BasicBackground b = new BasicBackground(fill);
        this.background = b;
    }

    /**
     *
     * @param imageName background image.
     */
    public void setBackground(String imageName) {
        Fill fill = new ImageFill(imageName,
                new Rectangle(new Point(0, 0), 0, 0));
        this.background = new BasicBackground(fill);
    }

    /**
     *
     * @param name the name of the level.
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

    /**
     *
     * @param s the paddle's speed.
     */
    public void setPaddleSpeed(int s) {
        this.paddleSpeed = s;
    }

    /**
     *
     * @param wid paddle's width.
     */
    public void setPaddleWidth(int wid) {
        this.paddleWidth = wid;
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
        return this.velocities;
    }
    /**
     * @return the speed of the paddle.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return the width of the paddle.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }
    /**
     * @return the level name that will be displayed at the top of the screen.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        return this.background;
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
     * @return the number of blocks that should be removed in order to consider this level to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
}