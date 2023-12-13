import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class LayerPanel extends JPanel {
    public boolean locked;
    public boolean contentVisible; 
    public LayerPanel(Layer layer, boolean locked) {
        this.locked = locked;
        this.contentVisible = layer.isContentVisible();
        setSize(new Dimension(100, 1000));
        setBackground(Color.WHITE);
    }

    public void select() {
        setBackground(Color.CYAN);
    }
    
    public void deselect() {
        setBackground(Color.WHITE);
    }
}
