import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LayerPanel extends JPanel {
    public static final int PANEL_HEIGHT = 50;
    public static final Dimension PANEL_MAX_SIZE = new Dimension(512, PANEL_HEIGHT);
    
    public boolean contentVisible; 
    public boolean selected;
    public final boolean locked;
    public final JButton hideButton;
    public final JButton selectButton;
    public final JTextField nameField;
    private final Layer layer;

    public LayerPanel(Layer layer, boolean locked, String label) {
        this.locked = locked;
        this.layer = layer;
        this.contentVisible = layer.isContentVisible();
        setPreferredSize(new Dimension(400, PANEL_HEIGHT));
        setMaximumSize(PANEL_MAX_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        hideButton = new JButton("Hide");
        hideButton.setPreferredSize(new Dimension(80, PANEL_HEIGHT));
        hideButton.setFocusable(false);
        add(hideButton);

        nameField = new JTextField(label);
        nameField.setPreferredSize(new Dimension(100, PANEL_HEIGHT));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setText(label);
        nameField.setEditable(!locked);
        add(nameField);
        
        selectButton = new JButton("Select Layer");
        selectButton.setPreferredSize(new Dimension(250, PANEL_HEIGHT));
        selectButton.setFocusable(false);
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
        hideButton.setText("Reveal");
        layer.setContentVisible(contentVisible);
    }

    public void revealLayer() {
        contentVisible = true;
        hideButton.setText("Hide");
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
