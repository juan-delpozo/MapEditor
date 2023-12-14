import java.awt.Graphics;

public class TileLayer extends Layer {
    public int type = 0;
    private Tile[][] tileGrid;
    private TileParser tileParser;

    public TileLayer(int numRows, int numCols, TileParser tileParser) {
        super(numRows, numCols);
        this.tileParser = tileParser;
        tileGrid = new Tile[numRows][numCols];
    }

    public Tile getTile(int row, int col) {
        return tileGrid[row][col];
    }

    public void setTile(int row, int col, Tile tile) {
        tileGrid[row][col] = tile;
    }

    @Override
    public void update(int row, int col, int code) {
        Tile tile = tileParser.getTile(code);
        setTile(row, col, tile);
    }

    @Override
    protected void draw(Graphics g, int row, int col, int tileWidth, int tileHeight) {
        Tile tile = getTile(row, col);
        
        if (tile == null) {
            return;
        }
        
        int x = col * tileWidth - Camera.x;
        int y = row * tileHeight - Camera.y;
        tile.draw(g, x, y, tileWidth, tileHeight);
    }
}
