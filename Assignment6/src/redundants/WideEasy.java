package redundants;

import characteristics.Velocity;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import levelsinfo.LevelInformation;
import sprites.Block;
import sprites.Sprite;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joffeda.
 */
public class WideEasy implements LevelInformation {
    private Rectangle gameSpace;
    private List<Block> blocks;
    /**
     * constructor.
     * @param width the width of the game window.
     * @param height the height of the game window.
     * @param bordersThick the game's borders' thick.
     */
    public WideEasy(int width, int height, int bordersThick) {
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
        for (int i = 1; i < 6; i++) {
            velocities.add(Velocity.fromAngleAndSpeed(12 * i, 400));
            velocities.add(Velocity.fromAngleAndSpeed(-12 * i, 400));
        }
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
        int paddleWidth = (int) (this.getGameSpace().getWidth() * 0.8);
        return paddleWidth;
    }

    /**
     * @return the level name that will be displayed at the top of the screen.
     */
    public String levelName() {
        return "Wide Easy";
    }

    /**
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        return new WideEasyBackground(this.getGameSpace());
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
        int obWidth = (int) ((this.gameSpace.getWidth() + 10) / 15);
        int obHeight = (int) (this.getGameSpace().getHeight() / 22);
        int x = (int) (this.getGameSpace().getUpperLeft().getX());
        int y = (int) (this.getGameSpace().getUpperLeft().getY());
        Point upperLeft = new Point(x, y + (8 * obHeight));

        for (int j = 0; j < 15; j++) { //one line of obstacles.
            Block block = new Block(upperLeft, obWidth, obHeight);
            block.setColor(this.colorByIndex(j));
            block.setColisionAmount(1);
            this.blocks.add(block);
            upperLeft = new Point(upperLeft.getX() + obWidth, upperLeft.getY()); // moving right.
        }
    }
    /**
     * @param index the block's index.
     * @return the block's color, defined by it's index.
     */
    public Color colorByIndex(int index) {
            if (index == 1 || index == 0) {
                return Color.RED;
            }
            if (index == 2 || index == 3) {
                return Color.ORANGE;
            }
            if (index == 4 || index == 5) {
                return Color.YELLOW;
            }
            if (index == 9 || index == 10) {
                return Color.BLUE;
            }
            if (index == 11 || index == 12) {
                return Color.PINK;
            }
            if (index == 13 || index == 14) {
                return Color.CYAN;
            }
            return Color.GREEN;
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