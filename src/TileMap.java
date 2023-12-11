import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TileMap {
    char[][][] layers;
    TileAtlas tileAtlas;
    String code; // Single code for all layers
    int S;
    int currentLayer; // Added currentLayer variable

    public TileMap(char[][][] layers, TileAtlas tileAtlas, int scale, String code) {
        this.layers = layers;
        this.tileAtlas = tileAtlas;
        this.S = scale;
        this.code = code;
        this.currentLayer = 0; // Initialize currentLayer to 0
        loadMapAssets();
    }

    public TileMap(String filename, TileAtlas tileAtlas, int scale, String code) {
        loadMap(filename);
        this.tileAtlas = tileAtlas;
        this.S = scale;
        this.code = code;
        this.currentLayer = 0; // Initialize currentLayer to 0

        if (layers != null) {
            loadMapAssets();
        } else {
            System.out.println("Error: The layers array is null.");
        }
    }

    public void loadMap(String filename) {
        File file = new File("src/txtmaps/" + filename);

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            int numLayers = Integer.parseInt(input.readLine()); // How many layers in the map?
            System.out.println(numLayers);
            int numRows = Integer.parseInt(input.readLine()); // How many rows in each layer?
            System.out.println(numRows);
            int numCols = Integer.parseInt(input.readLine()); // How many columns in each layer?

            layers = new char[numLayers][numRows][numCols];

            for (int layer = 0; layer < numLayers; layer++) {
                for (int row = 0; row < numRows; row++) {
                    String line = input.readLine();
                    for (int col = 0; col < numCols; col++) {
                        layers[layer][row][col] = line.charAt(col);
                    }
                }
            }

            System.out.println("Map Layers: " + numLayers);
            System.out.println("Map Rows: " + numRows);
            System.out.println("Map Columns: " + numCols);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMapAssets() {
        // You don't need to load individual tiles here since you're using TileAtlas
        // Assuming TileAtlas has already been set up with the tilesheet
    }

    public void draw(Graphics g) {
        int startLayer = 0;  // Set the starting layer to draw
        int endLayer = currentLayer;  // Set the ending layer to draw

        if (currentLayer == 0) {
            // If on layer 0, only draw layer 0
            endLayer = 0;
        }

        for (int layer = startLayer; layer <= endLayer; layer++) {
            for (int row = 0; row < layers[layer].length; row++) {
                for (int col = 0; col < layers[layer][row].length; col++) {
                    char c = layers[layer][row][col];

                    // Check if the character is not "." for the current layer
                    if (c != '.') {
                        // Find the index of the character in the code
                        int tileIndex = code.indexOf(c);

                        // Check if the character is in the code
                        if (tileIndex != -1) {
                            // Use tileAtlas to get the tile at the specified index
                            Tile tile = tileAtlas.getTile(tileIndex);

                            if (tile != null) {
                                Image tileImage = tile.getImage();

                                // Adjust the drawing position based on the tile size and spacing
                                int x = col * S - Camera.x;
                                int y = row * S - Camera.y;

                                g.drawImage(tileImage, x, y, S, S, null);
                            }
                        }
                    }
                }
            }
        }
    }

    public void saveMap(String filename) {
        File file = new File("src/txtmaps/" + filename);

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(layers.length + "\n");
            output.write(layers[0].length + "\n");
            output.write(layers[0][0].length + "\n");

            for (int layer = 0; layer < layers.length; layer++) {
                for (int row = 0; row < layers[layer].length; row++) {
                    for (int col = 0; col < layers[layer][row].length; col++) {
                        output.write(layers[layer][row][col]);
                    }
                    output.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[][][] getLayers() {
        return layers;
    }

    public void change(int x, int y, char c, int layer) {
        int row = (y + Camera.y) / S;
        int col = (x + Camera.x) / S;

        if (row >= 0 && row < layers[layer].length && col >= 0 && col < layers[layer][0].length) {
            layers[layer][row][col] = c;
        } else {
            System.out.println("Invalid row or column: " + row + ", " + col);
        }
    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(int layer) {
        if (layer >= 0 && layer < layers.length) {
            currentLayer = layer;
        } else {
            System.out.println("Invalid layer: " + layer);
        }
    }

    public String getCode() {
        return code;
    }
}
