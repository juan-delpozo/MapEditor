import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MapPanel extends GridPanel {

    TileMapEditor editor;
    TileParser tileParser;
    TilePanel tilePN;
    TileMap tilemap;
    
    int lastMouseX, lastMouseY;


    public MapPanel(TileMapEditor editor) {
        super(editor.tilemap.scale);
        this.editor = editor;
        this.tilemap = editor.tilemap;
        this.tileParser = editor.tileParser;    
        setupMouseDragging();
    }

    private void handleMouse(MouseEvent e) {
        int activeTilePanelIndex = editor.activeTilePanelIndex;
        if (activeTilePanelIndex == tileParser.invalidAtlasIndex) {
            return;
        }

        int activeTileIndex = editor.getTilePanel(activeTilePanelIndex).activeTileIndex;
        int code = tileParser.getCode(activeTilePanelIndex, activeTileIndex);

        int layer = editor.getCurrentLayer();

        if (lastMouseX >= 0 && lastMouseY >= 0) {
            tilemap.change(e.getX(), e.getY(), code, layer);
        } else {
            tilemap.change(e.getX(), e.getY(), code, layer);
        }

        lastMouseX = e.getX();
        lastMouseY = e.getY();

        repaint();
    }
    
    private void setupMouseDragging() {
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                handleMouse(e);
            }
        });
    }


    public void mousePressed(MouseEvent e) {
        handleMouse(e);
    }

    public void mouseReleased(MouseEvent e) {
        lastMouseX = -1;
        lastMouseY = -1;
    }

   
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                Camera.moveUp(scale);
                break;
            case KeyEvent.VK_S:
                Camera.moveDown(scale);
                break;
            case KeyEvent.VK_A:
                Camera.moveLeft(scale);
                break;
            case KeyEvent.VK_D:
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
