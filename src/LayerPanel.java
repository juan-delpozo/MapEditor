import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LayerPanel extends JPanel {
    public static final int PANEL_HEIGHT = 50;
    public boolean locked;
    public boolean contentVisible; 
    public boolean selected;
    public JButton hideButton;
    public JButton selectButton;
    private Layer layer;
    public LayerPanel(Layer layer, boolean locked) {
        this.locked = locked;
        this.layer = layer;
        this.contentVisible = layer.isContentVisible();
        setPreferredSize(new Dimension(400, PANEL_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        hideButton = new JButton("Hide Layer");
        hideButton.setPreferredSize(new Dimension(100, PANEL_HEIGHT));
        add(hideButton);
        selectButton = new JButton("Select Layer");
        selectButton.setPreferredSize(new Dimension(300, PANEL_HEIGHT));
        add(selectButton);

        setBackground(Color.WHITE);
    }

    public void onHideButtonPressed() {
        if (contentVisible == true) {
            hideLayer();
        } else {
            revealLayer();;
        }
    }

    public void hideLayer() {
        contentVisible = false;
        hideButton.setText("Reveal Layer");
        layer.setContentVisible(contentVisible);
    }

    public void revealLayer() {
        contentVisible = true;
        hideButton.setText("Hide Layer");
        layer.setContentVisible(contentVisible);
    }

    public void select() {
        selected = true;
        setBackground(Color.CYAN);
    }
    
    public void deselect() {
        selected = false;
        setBackground(Color.WHITE);
    }

}
