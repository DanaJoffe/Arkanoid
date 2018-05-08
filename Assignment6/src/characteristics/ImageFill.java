package characteristics;
import biuoop.DrawSurface;
import geometryprimitives.Rectangle;
import geometryprimitives.Point;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author joffeda.
 */
public class ImageFill implements Fill, Serializable {
    private String imageName;
    private Image image;
    private Rectangle canvas;

    /**
     * constructor.
     * @param i imageName.
     * @param k a rectangle - where to fill the color.
     */
    public ImageFill(String i, Rectangle k) {
        this.basicConstructor(i);
        this.canvas = k;
    }

    /**
     * constructor.
     * @param i imageName.
     * @param upperLeft of the rectangle - where to fill the color.
     * @param width of the rectangle - where to fill the color.
     * @param height of the rectangle - where to fill the color.
     */
    public ImageFill(String i, Point upperLeft, double width, double height) {
        this.basicConstructor(i);
        this.canvas = new Rectangle(upperLeft, width, height);
    }

    /**
     * constructor.
     * @param i imageName.
     * @param upperLeft of the rectangle - where to fill the color.
     */
    public ImageFill(String i, Point upperLeft) {
        this.basicConstructor(i);
        this.canvas = new Rectangle(upperLeft, 0, 0);
    }

    /**
     * constructs the image that this fill is filling.
     * @param imageName1 imageName.
     */
    private void basicConstructor(String imageName1) {
        this.imageName = imageName1;
        this.image = null;
        try {
            InputStream file = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(imageName1);
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            iostuff.Error.printAndExit(e.toString(), "ImageFill: basicConstructor");
        }
    }

    /**
     * canvas with color.
     * @param d a Drawing Surface.
     */
    public void fillIn(DrawSurface d) {
        d.drawImage((int) this.canvas.getUpperLeft().getX(),
                (int) this.canvas.getUpperLeft().getY(), this.image); // draw the image at location 10, 20.
    }
    /**
     *
     * @param canvas1 a rectangle.
     * @return a new Image fill with a given frame.
     */
    public Fill withNewCanvas(Rectangle canvas1) {
        return new ImageFill(this.imageName, canvas1);
    }
}