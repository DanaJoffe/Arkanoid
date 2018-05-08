package gamerelated;

/**
 * @author joffeda.
 */
public class Counter {
    private int currentCount;

    /**
     * constructor.
     * @param initial the number to start the count from.
     */
    public Counter(int initial) {
        this.currentCount = initial;
    }
    /**
     * add number to current count.
     * @param number a number.
     */
    public void increase(int number) {
        this.currentCount += number;
    }

    /**
     * subtract number from current count.
     * @param number a number.
     */
    public void decrease(int number) {
        this.currentCount -= number;
    }

    /**
     * @return current count.
     */
    public int getValue() {
        return this.currentCount;
    }

    @Override
    public String toString() {
        return Integer.toString(this.getValue());
    }
}