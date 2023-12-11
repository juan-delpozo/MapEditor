import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

public class TilePanel extends GridPanel {

    TileMap tilemap;
    Rect selection;
    char activeTile = '.';
    String code;
    int rows = 12;
    int cols = 12;

    public TilePanel(TileMapEditor editor) {
        super(editor);
        initializeTilePanel();
    }

    private void initializeTilePanel() {
        scale = 32;  // Set the scale to 32
        box = new Rect(-100, 100, scale, scale);
        selection = new Rect(-100, 100, scale, scale);

        tilemap = editor.tilemap;
        code = tilemap.getCode();

        int panelSize = scale * 12;
        setBounds(820, 10, panelSize, panelSize);

        bgColor = Color.BLACK;
        setBackground(bgColor);
    }

    public void mousePressed(MouseEvent e) {
        updateActiveTile();
    }

    private void updateActiveTile() {
        int row = my / scale;
        int col = mx / scale;

        selection.setLocation(scale * col, scale * row);

        if (tilemap != null) {
            int index = cols * row + col;

            if (index < code.length()) {
                activeTile = code.charAt(index);
            } else {
                activeTile = '.';
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        drawLayerInfo(g);
        drawSelection(g);
        drawGridBox(g);
    }

    private void drawTiles(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = cols * row + col;
                if (index < tilemap.tileAtlas.getNumTiles()) {
                    Tile tile = tilemap.tileAtlas.getTile(index);
                    if (tile != null) {
                        Image tileImage = tile.getImage();

                        // Adjust the drawing coordinates and dimensions based on the scale
                        int x = scale * col + 4;
                        int y = scale * row + 2;
                        int width = scale - 8;
                        int height = scale - 8;
                        

                        g.drawImage(tileImage, x, y, width, height, null);
                    }
                }
            }
        }
    }


    private void drawLayerInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Layer: " + tilemap.getCurrentLayer(), 10, getHeight() - 10);
        repaint();
    }

    private void drawSelection(Graphics g) {
        g.setColor(Color.RED);
        selection.draw(g);
    }

    private void drawGridBox(Graphics g) {
        g.setColor(Color.GREEN);
        if (mouseWithin)
            box.draw(g);
    }
}
