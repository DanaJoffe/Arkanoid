package screensandanimations;

/**
 * @author joffeda.
 * @param <T> a return type. what to return when getStatus is called.
 */
public interface Menu<T> extends Animation {
    /**
     * this creates a MenuItem<T>.
     * @param key pressing key.
     * @param message a message to show.
     * @param returnVal what to return when key is pressed.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     *
     * @return the status of the menu - what should be done now.
     */
    T getStatus();

    /**
     *
     * @param key pressing key.
     * @param message a message to show.
     * @param subMenu a menu to open when key is pressed.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}