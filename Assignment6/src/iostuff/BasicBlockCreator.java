package iostuff;
import characteristics.ColorFill;
import characteristics.Fill;
import characteristics.ImageFill;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import sprites.Block;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author joffeda.
 * this class will creat one type of blocks.
 */
public class BasicBlockCreator implements BlockCreator {
    private Integer width;
    private Integer height;
    private Integer hitPoints;
    private Map<Integer, Fill> fillOptions; //default fill will be the key -1.
    private Color stroke;

    /**
     * constructor.
     */
    public BasicBlockCreator() {
        this.width = null;
        this.height = null;
        this.hitPoints = null;
        this.fillOptions = new TreeMap<Integer, Fill>();
        this.stroke = null;
    }

    /**
     *
     * @param w block's width.
     */
    public void setWidth(int w) {
        this.width = w;
    }

    /**
     *
     * @param h block's height.
     */
    public void setHeight(int h) {
        this.height = h;
    }

    /**
     *
     * @param hit block's hit points amount.
     */
    public void setHitPoints(int hit) {
        this.hitPoints = hit;
    }

    /**
     *
     * @param c block's frame color.
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }

    /**
     *
     * @param op the number of hit points left for the block.
     * @param c the color we want the block to have when it has op hit points left.
     */
    public void addFillOption(Integer op, Color c) {
        this.fillOptions.put(op, new ColorFill(c, new Rectangle(new Point(0, 0), 0, 0)));
    }

    /**
     *
     * @param op the number of hit points left for the block.
     * @param imageName the path to the image we want the block to have when it has op hit points left.
     */
    public void addFillOption(Integer op, String imageName) {
        this.fillOptions.put(op, new ImageFill(imageName,
                new Rectangle(new Point(0, 0), 0, 0)));
    }

    /**
     *
     * @return true if this block creator has enough data to create a block.
     */
    public boolean isValid() {
        return (this.width != null && this.height != null && this.hitPoints != null);
    }

    /**
     * Create a block at the specified location.
     * @param xpos the x value of the upper left point of the block we want to create now.
     * @param ypos the y value of the upper left point of the block we want to create now.
     * @return a block.
     */
    public Block create(int xpos, int ypos) {
        if (!this.isValid()) {
            Error.printAndExit("invalid block creator", "BasicBlockCreator:create");
        }
        if ((this.fillOptions.isEmpty() || (!this.fillOptions.containsKey(-1)) &&  !this.fillOptions.containsKey(1))
                && this.stroke == null) {
            Error.print("block creator has no default fill nor stroke.\nyou may not see this block on screen",
                    "BasicBlockCreator:create");
        }
        Block b = new Block(new Point(xpos, ypos), this.width, this.height);
        if (this.stroke != null) {
            b.setFrameColor(this.stroke);
        }
        for (Integer option: this.fillOptions.keySet()) {
            b.addFillOption(option, this.fillOptions.get(option));
        }
        b.setColisionAmount(this.hitPoints);
        return b;
    }
}