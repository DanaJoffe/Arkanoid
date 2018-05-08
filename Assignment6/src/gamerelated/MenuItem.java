package gamerelated;

/**
 * this class is an assistant for Menu<T> class.
 * @author joffeda.
 * @param <T> a return type. what to return when a specific key is pressed.
 */
public class MenuItem<T> {
    private String key;
    private String message;
    private T returnVal;

    /**
     * constructor.
     * @param k a pressing key.
     * @param me a message to show on Menu.
     * @param reVal what to return when key is pressed.
     */
    public MenuItem(String k, String me, T reVal) {
        this.key = k;
        this.message = me;
        this.returnVal = reVal;
    }

    /**
     *
     * @return the key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @return the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     *
     * @return the return value.
     */
    public T getReturnVal() {
        return this.returnVal;
    }
}