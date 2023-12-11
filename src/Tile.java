import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * A class that represents an image for the rendering engine.
 */
public class Tile {
    /**
     * The image this tile represents.
     */
    public final BufferedImage image;

    /**
     * The width of the tile's image.
     */
    public final int width;

    /**
     * The height of the tile's image.
     */
    public final int height;

    /**
     * Creates a Tile from the give file path.
     * @param filePath the path to an image file
     * @throws IOException if an I/O error occurs.
     */
    public Tile(String filePath) throws IOException {
        this(ImageIO.read(new File(filePath)));
    }

    /**
     * Creates a Tile from the given image.
     * @param image
     */
    public Tile(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    public void draw(Graphics g, int x, int y, int w, int h) {
        g.drawImage(image, x, y, w, h,null);
    }

    /**
     * @return {@link #image}
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return {@link #width}
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return {@link #height}
     */
    public int getHeight() {
        return height;
    }
    
}
