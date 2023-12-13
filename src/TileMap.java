import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TileMap {
    public int scale;
    public int numTilesX, numTilesY;
    public int[][][] serializedTileset;
    private TileParser tileParser;
    public ArrayList<Layer> layers;

    public TileMap(String fileName, TileParser tileParser, int scale) {
        this.tileParser = tileParser;
        this.scale = scale;
        loadMap(fileName);
    }

    public TileMap(TileParser tileParser, int scale, int numRows, int numCols) {
        this.tileParser = tileParser;
        this.numTilesX = numCols;
        this.numTilesY = numRows;
        layers = new ArrayList<Layer>();
        layers.add(new Layer(numTilesY, numTilesX));
        this.scale = scale;
    }

    public void loadMap(String fileName) {
        File file = new File("src/txtmaps/" + fileName);

        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            int numLayers = Integer.parseInt(input.readLine()); // How many layers in the map?
            numTilesX = Integer.parseInt(input.readLine()); // How many columns in each layer?
            numTilesY = Integer.parseInt(input.readLine()); // How many rows in each layer?

            layers = new ArrayList<>(numLayers);

            for (int i = 0; i < numLayers; i++) {
                Layer layer = new Layer(numTilesY, numTilesX);
                for (int row = 0; row < numTilesY; row++) {
                    String line = input.readLine();
                    String[] codes = line.split(",");
                    for (int col = 0; col < numTilesX; col++) {
                        int code = tileParser.parseString(codes[col]);
                        Tile tile = tileParser.getTile(code);
                        layer.setSerialized(row, col,code);
                        layer.setTile(row,col,tile);
                    }
                }
                layers.add(layer);
            }

            System.out.println("Map Layers: " + numLayers);
            System.out.println("Map Rows: " + numTilesY);
            System.out.println("Map Columns: " + numTilesX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMap(String fileName) {
        File file = new File("src/txtmaps/" + fileName);

        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            output.write(layers.size() + "\n");
            output.write(numTilesX + "\n");
            output.write(numTilesY + "\n");

            for (Layer layer : layers) {
                for (int row = 0; row < numTilesY; row++) {
                    for (int col = 0; col < numTilesX; col++) {
                        String code = tileParser.getString(layer.getSerialized(row, col));
                        String separator = col > 0 ? "," : "";
                        output.write(separator+code);
                    }
                    output.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void change(int x, int y, int code, int layerIndex) {
        int row = Math.floorDiv(y + Camera.y, scale);
        int col = Math.floorDiv(x + Camera.x, scale);
        
        if (row >= 0 && row < numTilesY && col >= 0 && col < numTilesX) {
            Tile tile = tileParser.getTile(code);
            Layer layer = layers.get(layerIndex);
            layer.setSerialized(row, col, code);;
            layer.setTile(row, col, tile);
        } else {
            System.out.println("Invalid row or column: " + row + ", " + col);
        }
        
    }

    public void draw(Graphics g) {
        for (Layer layer : layers) {
            if (!layer.contentVisible) {
                continue;
            }
            
            for (int row = 0; row < numTilesY; row++) {
                for (int col = 0; col < numTilesX; col++) {
                    Tile tile = layer.getTile(row, col);
                    
                    if (tile == null) {
                        continue;
                    }
                    
                    int x = col * scale - Camera.x;
                    int y = row * scale - Camera.y;
                    tile.draw(g, x, y, scale, scale);
                }
            }
        }
    }
}
