import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MapPanel extends GridPanel {

    TileMapEditor editor;
    TileParser tileParser;
    TilePanel tilePN;
    TileMap tilemap;

    public MapPanel(TileMapEditor editor) {
        super(editor.tilemap.scale);
        this.editor = editor;
        this.tilemap = editor.tilemap;
        this.tileParser = editor.tileParser;
    }

    public void mousePressed(MouseEvent e) {
        int activeTilePanelIndex = editor.activeTilePanelIndex;
        if (activeTilePanelIndex == tileParser.invalidAtlasIndex) {
            return;
        }

        int activeTileIndex = editor.getTilePanel(activeTilePanelIndex).activeTileIndex;
        int code = tileParser.getCode(activeTilePanelIndex, activeTileIndex);

        int layer = editor.getCurrentLayer(); // Use the current layer
        tilemap.change(mx, my, code, layer);
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                Camera.moveUp(scale);
                break;
            case KeyEvent.VK_DOWN:
                Camera.moveDown(scale);
                break;
            case KeyEvent.VK_LEFT:
                Camera.moveLeft(scale);
                break;
            case KeyEvent.VK_RIGHT:
                Camera.moveRight(scale);
                break;
            case KeyEvent.VK_SPACE:
                int activeTilePanelIndex = editor.activeTilePanelIndex;

                int activeTileIndex = activeTilePanelIndex == tileParser.invalidAtlasIndex ? tileParser.invalidTileIndex : editor.getTilePanel(activeTilePanelIndex).activeTileIndex;
                int code = tileParser.getCode(activeTilePanelIndex, activeTileIndex);
                int layer = editor.getCurrentLayer();
                tilemap.change(mx, my, code, layer);
                break;
            // case KeyEvent.VK_0: // Change to layer 0
            // case KeyEvent.VK_1: // Change to layer 1
            // case KeyEvent.VK_2: // Change to layer 2
            //     int layerNumber = keyCode - KeyEvent.VK_0;
            //     editor.setCurrentLayer(layerNumber);
            //     System.out.println("Current Layer: " + editor.getCurrentLayer());
            //     break;
        }

        repaint();
    }

    @Override
    protected void draw(Graphics g) {
        tilemap.draw(g);
    }
}
