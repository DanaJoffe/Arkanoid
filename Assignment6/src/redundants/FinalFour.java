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
public class FinalFour implements LevelInformation {
    private Rectangle gameSpace;
    private List<Block> blocks;
    /**
     * constructor.
     * @param width the width of the game window.
     * @param height the height of the game window.
     * @param bordersThick the game's borders' thick.
     */
    public FinalFour(int width, int height, int bordersThick) {
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
        velocities.add(Velocity.fromAngleAndSpeed(45, 400));
        velocities.add(Velocity.fromAngleAndSpeed(-45, 400));
        velocities.add(Velocity.fromAngleAndSpeed(0, 400));
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
        return "Final Four";
    }

    /**
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        return new FinalFourBackground(this.getGameSpace());
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
        int obHeight = (int) (this.gameSpace.getHeight() / 22);
        int obWidth = (int) ((this.gameSpace.getWidth() + 10) / 15);
        int x = (int) (this.getGameSpace().getUpperLeft().getX());
        int y = (int) (this.getGameSpace().getUpperLeft().getY());
        Point upperLeft = new Point(x + this.getGameSpace().getWidth() - obWidth,
                y + 2.25 * obHeight);
        for (int i = 1; i < 8; i++) { //go over lines
            for (int j = 0; j < 15; j++) { //one line of obstacles.
                Block block = new Block(upperLeft, obWidth, obHeight);
                block.setColor(this.colorByLine(i));
                block.setColisionAmount(this.colisionAmountByLine(i));
                this.blocks.add(block);
                upperLeft = new Point(upperLeft.getX() - obWidth,
                        upperLeft.getY()); // moving left.
            }
            upperLeft = new Point(x + this.getGameSpace().getWidth() - obWidth,
                    y + (2.25 + i) * obHeight);
        }
    }

    /**
     * @param line the lime number.
     * @return how many collisions should the block take, defined by it's line
     * location.
     */
    public int colisionAmountByLine(int line) {
        if (line == 1) {
            return 2;
        } else {
            return 1;
        }
    }
    /**
     * @param line the lime number.
     * @return the block's color, defined by it's line location.
     */
    public Color colorByLine(int line) {
        if (line == 1) {
            return Color.GRAY;
        }
        if (line == 2) {
            return Color.RED;
        }
        if (line == 3) {
            return Color.YELLOW;
        }
        if (line == 4) {
            return Color.GREEN;
        }
        if (line == 5) {
            return Color.WHITE;
        }
        if (line == 6) {
            return Color.PINK;
        }
        return Color.CYAN;
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