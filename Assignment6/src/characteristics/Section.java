package characteristics;

/**
 * @author joffeda.
 */
public class Section {
    private int smallX;
    private int bigX;

    /**
     * constructor.
     * @param start the start of the section.
     * @param end the end of the section.
     */
    public Section(int start, int end) {
        this.smallX = start;
        this.bigX = end;
    }
    /**
     * @return the start of the section.
     */
    public int smallX() {
        return this.smallX;
    }
    /**
     * @return the end of the section.
     */
    public int bigX() {
        return this.bigX;
    }
    /**
     * @param x a number.
     * @return true if x is in the section, else false.
     */
    public boolean isInSection(int x) {
        if (x <= this.bigX() && x >= this.smallX()) {
            return true;
        }
        return false;
    }
}
