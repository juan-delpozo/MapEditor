import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GridPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    // Background color
    Color bgColor = Color.LIGHT_GRAY;

    // Scale for the grid
    int scale;

    // Rectangle to represent the cursor
    Rect box;

    // Mouse coordinates
    int mx;
    int my;

    // Flag to indicate if the mouse is within the panel
    boolean mouseWithin = false;

    // Reference to the TileMapEditor

    public GridPanel(int scale) {
        this.scale = scale;

        // Initialize the cursor rectangle
        this.box = new Rect(-100, 100, scale, scale);

        // Set up event listeners
        setBackground(bgColor);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true); // Needed for key events
    }

    // Update cursor position on mouse movement
    public void mouseMoved(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        box.setLocation(mx / scale * scale, my / scale * scale);
        repaint();
    }
    
    // Update cursor position when mouse is dragged
    public void mouseDragged(MouseEvent e) {
        mx = e.getX();
        my = e.getY();
        box.setLocation(mx / scale * scale, my / scale * scale);
        repaint();
    }

    // Other mouse events (not used in this example)
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
        mouseWithin = true;
        repaint();
    }
    public void mouseExited(MouseEvent e) {
        mouseWithin = false;
        repaint();
    }

    // Other key events (not used in this example)
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    // Paint the component
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

        // Draw cursor if the mouse is within the panel
        g.setColor(Color.GREEN);
        if (mouseWithin)
            box.draw(g);
    }

    protected void draw(Graphics g) {}
}



