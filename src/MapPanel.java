import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MapPanel extends GridPanel {

    TilePanel tilePN;
    TileMap tilemap;

    public MapPanel(TileMapEditor editor) {
        super(editor);
        tilePN = editor.tilePN;
        tilemap = editor.tilemap;
        setBounds(10, 10, 800, 448);
        setBackground(bgColor);
    }

    public void mousePressed(MouseEvent e) {
        if (tilePN == null) {
            System.out.println("No TilePanel");
        } else {
            char activeTile = tilePN.activeTile;
            int layer = tilemap.getCurrentLayer(); // Use the current layer
            tilemap.change(mx, my, activeTile, layer);
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
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
                char activeTile = tilePN.activeTile;
                int layer = tilemap.getCurrentLayer();
                tilemap.change(mx, my, activeTile, layer);
                break;
            case KeyEvent.VK_0: // Change to layer 0
            case KeyEvent.VK_1: // Change to layer 1
            case KeyEvent.VK_2: // Change to layer 2
                int layerNumber = code - KeyEvent.VK_0;
                tilemap.setCurrentLayer(layerNumber);
                System.out.println("Current Layer: " + tilemap.getCurrentLayer());
                break;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tilemap.draw(g);
        g.setColor(Color.GREEN);
        if (mouseWithin)
            box.draw(g);
    }
}
