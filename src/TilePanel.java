import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;


public class TilePanel extends GridPanel {
    TileAtlas tileAtlas;
    TileParser tileParser;
    Rect selection;
    int activeTileIndex = -1;
    String code;
    int rows, cols;

    public TilePanel(TileAtlas atlas, TileParser tileParser) {
        super(32);
        this.tileParser = tileParser;
        this.tileAtlas = atlas;
        rows = atlas.numTilesY;
        cols = atlas.numTilesX;
        activeTileIndex = tileParser.invalidTileIndex;
        initializeTilePanel();
    }

    private void initializeTilePanel() {
        selection = new Rect(-100, 100, scale, scale);

        bgColor = Color.BLACK;
        setBackground(bgColor);
        setPreferredSize(new Dimension(scale * cols, scale * rows));
    }

    public void mousePressed(MouseEvent e) {
        updateActiveTile();
    }

    private void updateActiveTile() {
        int row = Math.floorDiv(my, scale);
        int col = Math.floorDiv(mx, scale);

        selection.setLocation(scale * col, scale * row);

        int index = cols * row + col;
        
        if (row < rows && col < cols) {
            activeTileIndex = index;

        } else {
            activeTileIndex = tileParser.invalidTileIndex;
        }
    }

    @Override
    protected void draw(Graphics g) {
        drawTiles(g);
        drawSelection(g);
    }

    private void drawTiles(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = cols * row + col;
                Tile tile = tileAtlas.getTile(index);
                int x = scale * col;
                int y = scale * row;
                g.drawImage(tile.getImage(), x, y, scale, scale, null);
            }
        }
    }

    private void drawSelection(Graphics g) {
        g.setColor(Color.RED);
        selection.draw(g);
    }
}
