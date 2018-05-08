package characteristics;

import java.awt.Color;
import java.util.Random;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author joffeda.
 */
public class ColorInfo {
    private Color color; //becomes the mapped value of -1, in fillingOptions.
    private Color frameColor;
    private boolean perminent;
    private Map<Integer, Fill> fillingOptions;

    /**
     * constructor.
     * default setup: can change color, frame color = BLACK.
     */
    public ColorInfo() {
        this.perminent = false; //default
        this.fillingOptions = new TreeMap<Integer, Fill>();
    }

    /**
     * @param op a number that maps to a specific fill.
     * @param fill a Fill object - can be an image or a color.
     */
    public void addFillOption(Integer op, Fill fill) {
        this.fillingOptions.put(op, fill);
    }

    /**
     *
     * @return the map of the different fillings and their keys.
     */
    public Map<Integer, Fill> getFillOptions() {
        return this.fillingOptions;
    }

    /**
     * make the object not possible to change color.
     */
    public void makePrminent() {
        this.perminent = true;
    }
    /**
     * make the object possible to change color.
     */
    public void makeVariable() {
        this.perminent = false;
    }
    /**
     * @return true if the object is not capable of color change, and false otherwise.
     */
    public boolean isPerminent() {
        return this.perminent;
    }
    /**
     * sets the main color of the object.
     * @param color1 a color.
     */
    public void set(Color color1) {
        this.color = color1;
    }
    /**
     * sets the frame color of the object.
     * @param color1 a color.
     */
    public void setFrameColor(Color color1) {
        this.frameColor = color1;
    }
    /**
     * @return the main color of the object.
     */
    public Color get() {
        return this.color;
    }
    /**
     * @return the frame color of the object.
     */
    public Color getFrameColor() {
        return this.frameColor;
    }
    /**
     * @return a random color.
     */
    public Color randomColor() {
        Random rand = new Random(); // create a random-number generator
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }
}