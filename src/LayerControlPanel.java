import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LayerControlPanel extends JPanel {
    private static final Dimension SCROLL_SIZE = new Dimension(400, 300);
    private ArrayList<LayerPanel> layerPanels;
    private JScrollPane layerScrollPane;
    private JPanel view;
    public JButton addLayerButton;
    public JButton deleteLayerButton;
    public int selectedIndex = 0;

    public LayerControlPanel(TileMapEditor editor, ArrayList<Layer> layers) {
        this.layerPanels = new ArrayList<LayerPanel>(layers.size());
        setPreferredSize(new Dimension(512, 400));
        setSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        
        setBackground(Color.GRAY);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        JScrollPane layerScrollPane = createLayerScrollPane(layers);
        add(layerScrollPane, constraints);
        
        // Create Buttons
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 1;
        constraints.weighty = 0;
        JPanel buttonPanel = createButtoPanel();
        add(buttonPanel, constraints);
    }

    private JScrollPane createLayerScrollPane(ArrayList<Layer> layers) {
        view = new JPanel();
        BoxLayout layout = new BoxLayout(view, BoxLayout.Y_AXIS);
        view.setLayout(layout);
        view.setPreferredSize(SCROLL_SIZE);
        view.setBackground(Color.BLACK);
        
        view.add(Box.createHorizontalGlue());

        for (int i = 0; i < layers.size(); i++) {
            addLayer(view, layers.get(i), i == 0);
        }

        layerScrollPane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        layerScrollPane.getVerticalScrollBar().setUnitIncrement(50);
        layerScrollPane.getVerticalScrollBar().setBlockIncrement(50);
        layerScrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        layerScrollPane.getHorizontalScrollBar().setBlockIncrement(50);
        layerPanels.get(0).select();
        
        return layerScrollPane;
    }

    private JPanel createButtoPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 50));
        buttonPanel.setSize(buttonPanel.getPreferredSize());
        buttonPanel.setBackground(Color.BLACK);

        addLayerButton = new JButton("Add Layer");
        Dimension buttonSize = new Dimension(128, 48);
        addLayerButton.setPreferredSize(buttonSize);
        buttonPanel.add(addLayerButton);
        
        deleteLayerButton = new JButton("Delete Layer");
        deleteLayerButton.setPreferredSize(buttonSize);
        buttonPanel.add(deleteLayerButton);

        return buttonPanel;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        this.layerPanels.get(this.selectedIndex).deselect();
        this.selectedIndex = index;
        this.layerPanels.get(selectedIndex).select();
    }

    private void adjustScrollPane() {
        int sizeY = Math.max(LayerPanel.PANEL_HEIGHT * layerPanels.size(), SCROLL_SIZE.height);
        view.setPreferredSize(new Dimension(SCROLL_SIZE.width, sizeY));
        view.setSize(view.getPreferredSize());
    }

    private LayerPanel addLayer(JPanel view, Layer layer, boolean locked) {
        int size = layerPanels.size();
        String name = size == 0 ? "Collision Layer" : "New Layer"; 
        LayerPanel layerPanel = new LayerPanel(layer, locked, name);
        layerPanels.add(layerPanel);
        view.add(layerPanel, 0);
        adjustScrollPane();
        
        return layerPanel;
    }

    public LayerPanel addLayerPanel(Layer layer) {
        LayerPanel layerPanel = addLayer(view, layer, false);
        setSelectedIndex(layerPanels.size() - 1);
        view.revalidate();
        repaint();

        return layerPanel;
    }

    private void removeLayer(int index) {
        if (index == 0) { // Collision Layer cant be removed
            return;
        }

        LayerPanel layerPanel = layerPanels.remove(index);
        view.remove(layerPanel);
        adjustScrollPane();
    }

    public void deleteLayerPanel(int index) {
        if (index == 0) { // Collision Layer cant be removed
            System.out.println("Cannot delete Collision Layer");
            return;
        }

        if (index == selectedIndex) {
            setSelectedIndex(selectedIndex - 1);
        }

        removeLayer(index);
        view.revalidate();
        repaint();
    }

    public ArrayList<LayerPanel> getLayerPanels() {
        return layerPanels;
    }

    public int getIndex(LayerPanel layerPanel) {
        return layerPanels.indexOf(layerPanel);
    }

    public int getNumLayers() {
        return layerPanels.size();
    }
}
