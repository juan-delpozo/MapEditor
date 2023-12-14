import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

public class TileMapEditor extends JFrame implements ActionListener, ComponentListener {

    public ArrayList<TilePanel> tilePanels;
    public int activeTilePanelIndex;
    public JMenuItem save;
    public MapPanel mapPN;
    public TileMap tilemap;
    public TileParser tileParser;
    public LayerControlPanel layerControlPanel;

    public TileMapEditor() {
        tilePanels = new ArrayList<>();
        save = new JMenuItem("Save");
        tileParser = new TileParser(AssetPool.getLandscapeAtlases());
        // tilemap = new TileMap(tileParser, 16, 25, 50);
        tilemap = new TileMap("map.map", tileParser, 16);
        mapPN = new MapPanel(this);
        mapPN.addComponentListener(this);
        mapPN.setPreferredSize(new Dimension(1024,800));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenuBar();
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5,15,5,15);
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1.0;

        add(mapPN, constraints);

        JPanel editorPanel = createEditorControlPanel();
        editorPanel.setPreferredSize(new Dimension(512, 800));
        editorPanel.setSize(editorPanel.getPreferredSize());
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 0;
        add(editorPanel, constraints);

        Camera.setLocation(0, 0);
        
        setTitle("Tile Map Editor");
        pack();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        save.addActionListener(this);
        fileMenu.add(save);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private JScrollPane createScrollPane(TileAtlas atlas) {
        TilePanel tilePanel = new TilePanel(atlas, tileParser);
        tilePanels.add(tilePanel);

        JScrollPane scrollPane = new JScrollPane(tilePanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(32);
        scrollPane.getVerticalScrollBar().setBlockIncrement(32);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(32);
        scrollPane.getHorizontalScrollBar().setBlockIncrement(32);
        
        return scrollPane;
    }

    private JTabbedPane createTileAtlasSelector() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(512, 400));
        tabbedPane.setSize(tabbedPane.getPreferredSize());
        tabbedPane.setMinimumSize(tabbedPane.getPreferredSize());
        tabbedPane.addTab("Fences", createScrollPane(AssetPool.getLandscapeAtlas(0)));
        tabbedPane.addTab("Grounds", createScrollPane(AssetPool.getLandscapeAtlas(1)));
        tabbedPane.addTab("Logs", createScrollPane(AssetPool.getLandscapeAtlas(2)));
        tabbedPane.addTab("Mushrooms", createScrollPane(AssetPool.getLandscapeAtlas(3)));
        tabbedPane.addTab("Trees", createScrollPane(AssetPool.getLandscapeAtlas(4)));
        tabbedPane.addTab("Wild Flowers", createScrollPane(AssetPool.getLandscapeAtlas(5)));
        tabbedPane.setSelectedIndex(0);

        return tabbedPane;
    }

    private void registerLayerPanel(LayerPanel layerPanel) {
        layerPanel.hideButton.addActionListener((ActionEvent hideEvent) -> {
            layerPanel.onHideButtonPressed();
            mapPN.repaint();
        });
        
        layerPanel.selectButton.addActionListener((ActionEvent event) -> {
            int index = layerControlPanel.getIndex(layerPanel);
            layerControlPanel.setSelectedIndex(index);
        });
    }

    private JPanel createEditorControlPanel() {
        JPanel editorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.5;

        layerControlPanel = new LayerControlPanel(this, tilemap.getLayers());
        for (LayerPanel layerPanel : layerControlPanel.getLayerPanels()) {
            registerLayerPanel(layerPanel);
        }

        layerControlPanel.addLayerButton.addActionListener((ActionEvent event) -> {
            Layer layer = tilemap.addLayer();
            registerLayerPanel(layerControlPanel.addLayerPanel(layer));
        });

        layerControlPanel.deleteLayerButton.addActionListener((ActionEvent event) -> {
            int layerIndex = layerControlPanel.getSelectedIndex();
            if (layerIndex == 0) { // Collision Layer cant be removed
                System.out.println("Cannot delete Collision Layer");
                return;
            }

            tilemap.removeLayer(layerIndex);
            layerControlPanel.deleteLayerPanel(layerIndex);
            mapPN.repaint();
        });

        editorPanel.add(layerControlPanel, constraints);
        
        JTabbedPane tileAtlasSelector = createTileAtlasSelector();
        activeTilePanelIndex = tileAtlasSelector.getSelectedIndex();
        tileAtlasSelector.addChangeListener((ChangeEvent e) -> {
            activeTilePanelIndex = tileAtlasSelector.getSelectedIndex();
            activeTilePanelIndex = activeTilePanelIndex != -1 ? activeTilePanelIndex : tileParser.invalidAtlasIndex;
        });

        editorPanel.add(tileAtlasSelector, constraints);

        return editorPanel;
    }

    public TilePanel getTilePanel(int panelIndex) {
        return tilePanels.get(panelIndex);
    }

    
    public int getCurrentLayer() {
        return layerControlPanel.getSelectedIndex();
    }

    public void setCurrentLayer(int layer) {
        layerControlPanel.setSelectedIndex(layer % layerControlPanel.getNumLayers());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            tilemap.saveMap("map.map");
            System.out.println("Saved map to bin folder.");
        }
    }

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension currentSize = e.getComponent().getSize();
		Camera.setSize(currentSize.width, currentSize.height);
	}

	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
    
    public static void main(String[] args) {
        AssetPool.load();
        SwingUtilities.invokeLater(() -> {
            TileMapEditor editor = new TileMapEditor();
            editor.setVisible(true);
        });
    }
}

