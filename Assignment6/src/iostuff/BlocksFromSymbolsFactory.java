package iostuff;
import sprites.Block;
import java.util.Map;
import java.util.TreeMap;
/**
 * @author joffeda.
 */
public class BlocksFromSymbolsFactory { //per Level
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<String, Integer>();
        this.blockCreators = new TreeMap<String, BlockCreator>();
    }

    /**
     *
     * @param symbol a space symbol.
     * @param width the space width.
     */
    public void addSpacer(String symbol, Integer width) {
        this.spacerWidths.put(symbol, width);
    }

    /**
     *
     * @param symbol a block symbol.
     * @param blockCreator a blockCreator that will creat blocks of symbol given.
     */
    public void addBlockCreator(String symbol, BlockCreator blockCreator) {
        this.blockCreators.put(symbol, blockCreator);
    }

    /**
     *
     * @param s a String.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     *
     * @param s a String.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     *
     * @param s a block symbol.
     * @param x the x value of the upper left point of the block' position.
     * @param y the y value of the upper left point of the block' position.
     * @return a block according to the definitions associated.
     */
    public Block getBlock(String s, int x, int y) {
        if (!this.isBlockSymbol(s)) {
            Error.printAndExit("invalid block symbol", "BlocksFromSymbolsFactory: getBlock");
        }
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     *
     * @param s a space symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}