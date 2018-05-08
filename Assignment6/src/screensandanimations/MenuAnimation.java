package screensandanimations;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import characteristics.Fill;
import characteristics.ImageFill;
import gamerelated.MenuItem;
import java.util.ArrayList;
import java.util.List;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
/**
 * @author joffeda.
 * @param <T> a return type. what to return when getStatus is called.
 */
public class MenuAnimation<T> implements Menu<T> { //returns Tasks<Void> - things that can run, and return nothing.
    private T status;
    private String menuTitle;
    private List<MenuItem<T>> selections;
    private KeyboardSensor keyboard;
    private boolean stop;
    private Fill background;

    private Menu<T> subMenu;
    private String subMenuKey;
    private boolean isSubMenuRunning;

    /**
     * constructor.
     * @param name the menu's title.
     * @param key keyboardSensor.
     * @param backgroundName a path of a background image.
     */
    public MenuAnimation(String name, KeyboardSensor key, String backgroundName) {
        this.basicConstructor(name, key);
        this.setBackground(backgroundName);
    }

    /**
     * constructor.
     * @param name the menu's title.
     * @param key keyboardSensor.
     */
    public MenuAnimation(String name, KeyboardSensor key) {
        this.basicConstructor(name, key);
    }

    /**
     * all the constructors use this.
     * @param name the menu's title.
     * @param key keyboardSensor.
     */
    private void basicConstructor(String name, KeyboardSensor key) {
        this.keyboard = key;
        this.status = null;
        this.menuTitle = name;
        this.selections = new ArrayList<MenuItem<T>>();
        this.stop = false;

        this.subMenu = null;
        this.isSubMenuRunning = false;
    }

    /**
     *
     * @param key pressing key.
     * @param message a message to show.
     * @param returnVal what to return when key is pressed.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new MenuItem<T>(key, message, returnVal));
    }

    /**
     *
     * @return the status of the menu - what should be done now.
     */
    public T getStatus() {
        this.stop = false;
        T stat = this.status;
        this.status = null;
        return stat;
    }

    /**
     * turning the path in to a Fill object that can be shown.
     * @param backgroundPath a path of a background image.
     */
    private void setBackground(String backgroundPath) {
        Fill fill = new ImageFill(backgroundPath, new Rectangle(new Point(0, 0), 0, 0));
        this.background = fill;
    }
    /**
     * drawing one frame of the animation.
     * @param d a drawing surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (!this.isSubMenuRunning) {
            if (this.background != null) {
                this.background.fillIn(d);
            }
            //d.setColor(Color.BLACK);
            int x = d.getHeight() / 12;
            int y = d.getWidth() / 15;
            d.drawText(x, y, this.menuTitle, 32);

            y = d.getHeight() / 5;
            int xOp = d.getWidth() / 6;
            int gap = d.getHeight() / 12;

            if (!this.selections.isEmpty()) {
                for (MenuItem<T> option : this.selections) {
                    String stringOption = "(".concat(option.getKey()).concat(") ").concat(option.getMessage());
                    d.drawText(xOp, y, stringOption, 25);
                    y += gap;

                    //For SubMenu.
                    if (this.keyboard.isPressed(option.getKey())) {
                        if (this.subMenu != null && option.getKey().equals(this.subMenuKey)) {
                            this.isSubMenuRunning = true;
                        } else {
                            this.status = option.getReturnVal();
                            this.stop = true;
                        }
                    }

                }
            }

        } else {
            this.subMenu.doOneFrame(d, dt);
            if (this.subMenu.shouldStop()) {
                if (keyboard.isPressed("b")) {
                    this.isSubMenuRunning = false;
                    this.subMenu.getStatus();
                } else {
                    this.isSubMenuRunning = false;
                    this.status = this.subMenu.getStatus();
                    this.stop = true;
                }
            }
        }
    }

    /**
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     *
     * @param key pressing key.
     * @param message a message to show.
     * @param subMenu1 a menu to open when key is pressed.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu1) {
        this.subMenu = subMenu1;
        this.subMenuKey = key;
        this.addSelection(key, message, null);
    }
}