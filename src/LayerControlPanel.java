import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LayerControlPanel extends JPanel {
    ArrayList<LayerPanel> layerPanels;
    public JButton addLayer;
    public JButton deleteLayer;
    private TileMapEditor editor;
    public int selectedIndex = 0;

    public LayerControlPanel(TileMapEditor editor, ArrayList<Layer> layers) {
        this.editor = editor;
        this.layerPanels = new ArrayList<LayerPanel>(layers.size());
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.bottom = 15;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.8;
        // Create scrolling LayerPanel Viewer
        JScrollPane layerScrollPane = new JScrollPane();
        JPanel view = new JPanel(new GridBagLayout());
        view.setPreferredSize(new Dimension(400, 400));
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.anchor = GridBagConstraints.NORTH;
        panelConstraints.gridx = 0;
        panelConstraints.weightx = 1;
        layerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        layerScrollPane.setViewportView(view);
        for (int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            LayerPanel layerPanel = new LayerPanel(layer, i == 0);
            layerPanels.add(layerPanel);
            view.add(layerPanel, panelConstraints);
        }

        layerPanels.get(0).select();
        add(layerScrollPane, constraints);
        // Create Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        // addLayer = new JButton();
        // deleteLayer = new JButton();
        constraints.gridy = 1;
        constraints.weighty = 0.2;
        add(buttonPanel, constraints);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.layerPanels.get(this.selectedIndex).deselect();
        this.selectedIndex = selectedIndex;
        this.layerPanels.get(selectedIndex).select();
    }
}
