package iostuff;
import sprites.Block;
/**
 * @author joffeda.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the x value of the upper left point of the block we want to create now.
     * @param ypos the y value of the upper left point of the block we want to create now.
     * @return a block.
     */
    Block create(int xpos, int ypos);
}