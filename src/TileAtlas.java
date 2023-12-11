import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A class that splits an image into multiple {@link Tile Tiles}.
 */
public class TileAtlas extends Tile {
    private ArrayList<Tile> tiles;

    /**
     * Loads the image at the given file path.
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * @param filepath the path to the image file
     * @param tileWidth the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing the pixel spacing between subImages
     * @throws IOException if an I/O error occurs
     */ 
    public TileAtlas(String filepath, int tileWidth, int tileHeight, int spacing) throws IOException {
        this(ImageIO.read(new File(filepath)), tileWidth, tileHeight, spacing);
    }

    /**
     * Splits an image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * @param image
     * @param tileWidth the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing the pixel spacing between subImages
     */ 
    public TileAtlas(BufferedImage image, int tileWidth, int tileHeight, int spacing) {
        super(image);
        int rowIncrement = tileHeight + spacing;
        int columnIncrement = tileWidth + spacing;
        int maxRows = height / rowIncrement;
        int maxCols = width / columnIncrement;
        tiles = new ArrayList<>();

        sample(image, tileWidth, tileHeight, maxRows, maxCols, rowIncrement, columnIncrement);
    }

    /**
     * Loads the image at the given file path.
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * @param filepath the path to the image file
     * @param tileWidth the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing the pixel spacing between subImages
     * @param columns the number of subImages to make per row
     * @throws IOException if an I/O error occurs
     */ 
    public TileAtlas(String filepath, int tileWidth, int tileHeight, int spacing, int[] columns) throws IOException {
        this(ImageIO.read(new File(filepath)), tileWidth, tileHeight, spacing, columns);
    }
    
    /**
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * @param image
     * @param tileWidth the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing the pixel spacing between subImages
     * @param columns the number of subImages to make per row
     */ 
    public TileAtlas(BufferedImage image, int tileWidth, int tileHeight, int spacing, int[] columns) {
        super(image);
        int rowIncrement = tileHeight + spacing;
        int columnIncrement = tileWidth + spacing;
        int maxRows = height / rowIncrement;
        int maxCols = width / columnIncrement;
        tiles = new ArrayList<>();

        for (int row = 0; row < maxRows; row++) {
            int numCols = Math.min(columns[row], maxCols);
            int imageY = row * rowIncrement;
            for (int column = 0; column < numCols; column++) {
                int imageX = column * columnIncrement;
                tiles.add(new Tile(image.getSubimage(imageX, imageY, tileWidth, tileHeight)));
            }
        }
    }
    
    /**
     * Loads the image at the given file path.
     * Divides the image into a set of areas which will be sampled individually to create the TileAtlas. 
     * @param filepath the path to the image file
     * @param sampleAreas an array of {@link Rect Rects} defining areas of the input image to sample
     * @param tileDimensions a 2d array of the form {{tileWidth, tileHeight, spacing}, ...} defining tile dimensions per sample area
     * @throws IOException if an I/O error occurs
     */
    public TileAtlas(String filepath, Rect[] sampleAreas, int[][] tileDimensions) throws IOException {
        this(ImageIO.read(new File(filepath)), sampleAreas, tileDimensions);
    }
    
    /**
     * Divides the image into a set of areas which will be sampled individually to create the TileAtlas. 
     * @param image
     * @param sampleAreas an array of {@link Rect Rects} defining areas of the input image to sample
     * @param tileDimensions a 2d array of the form {{tileWidth, tileHeight, spacing}, ...} defining tile dimensions per sample area
     */
    public TileAtlas(BufferedImage image, Rect[] sampleAreas, int[][] tileDimensions) {
        super(image);
        tiles = new ArrayList<>();

        for (int i = 0; i < sampleAreas.length; i++) {
            Rect sampleArea = sampleAreas[i];
            int tileWidth = tileDimensions[i][0];
            int tileHeight = tileDimensions[i][1];
            int spacing = tileDimensions[i][2];
            int columnIncrement = tileWidth + spacing;
            int rowIncrement = tileHeight + spacing;
            int maxRows = sampleArea.getHeight() / rowIncrement;
            int maxCols = sampleArea.getWidth() / columnIncrement;

            BufferedImage subAtlas = image.getSubimage(
                    sampleArea.getX(),
                    sampleArea.getY(),
                    sampleArea.getWidth(),
                    sampleArea.getHeight()
            );

            sample(subAtlas, tileWidth, tileHeight, maxRows, maxCols, rowIncrement, columnIncrement);
        }
    }


    /**
     * Samples a rectangular area of the given image.
     * @param image the image to sample
     * @param tileWidth the width of the sub-images
     * @param tileHeight the height of the sub-images
     * @param maxRows the height of the image in number of tiles to sample
     * @param maxCols the width of the image in number of tiles to sample
     * @param rowIncrement 
     * @param columnIncrement
     */
    private void sample(BufferedImage image, int tileWidth, int tileHeight, int maxRows, int maxCols, int rowIncrement, int columnIncrement) {
        for (int row = 0; row < maxRows; row++) {
            int imageY = row * rowIncrement;
            for (int column = 0; column < maxCols; column++) {
                int imageX = column * columnIncrement;
                tiles.add(new Tile(image.getSubimage(imageX, imageY, tileWidth, tileHeight)));
            }
        }
    }

    /**
     * @return All {@link Tile Tiles} in this TileAtlas
     */
    public Tile[] getTiles() {
        return tiles.toArray(new Tile[tiles.size()]);
    }

    /**
     * @param n 
     * @return the nth {@link Tile} of the TileAtlas
     */
    public Tile getTile(int n) {
        return tiles.get(n);
    }

    /**
     * @return the number of tiles the TileAtlas was split into.
     */
    public int getNumTiles() {
        return tiles.size();
    }
}