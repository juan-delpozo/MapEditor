import java.awt.Color;
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
    // public TilePanel tilePN;

    public JMenuItem save;
    public MapPanel mapPN;
    // public TileAtlas tileAtlas;
    public TileMap tilemap;
    public TileParser tileParser;

    public TileMapEditor() {
        tilePanels = new ArrayList<>();
        save = new JMenuItem("Save");
        tileParser = new TileParser(AssetPool.getLandscapeAtlases());
        // tilemap = new TileMap(tileParser, 16, 25, 50);
        tilemap = new TileMap("map.map", tileParser, 16);
        mapPN = new MapPanel(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900);
        setTitle("Tile Map Editor");
        
        createMenuBar();
        
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(15,15,15,15);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 2.0 / 3.0;
        constraints.weighty = 1.0;

        add(mapPN, constraints);

        JPanel editorPanel = createEditorControlPanel();
        constraints.weightx = 1.0 / 3.0;
        constraints.gridx = 1;
        add(editorPanel, constraints);

        mapPN.addComponentListener(this);
        Camera.setLocation(0, 0);
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

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tilePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(32);
        scrollPane.getVerticalScrollBar().setBlockIncrement(32);
        
        return scrollPane;
    }

    private JPanel createEditorControlPanel() {
        JPanel editorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.bottom = 15;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.5;

        JPanel layerPanel = new JPanel(); // Placeholder until layerControlPanel is implemented
        layerPanel.setFocusable(true);
        layerPanel.setBackground(Color.GRAY);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Fences", createScrollPane(AssetPool.getLandscapeAtlas(0)));
        tabbedPane.addTab("Grounds", createScrollPane(AssetPool.getLandscapeAtlas(1)));
        tabbedPane.addTab("Logs", createScrollPane(AssetPool.getLandscapeAtlas(2)));
        tabbedPane.addTab("Mushrooms", createScrollPane(AssetPool.getLandscapeAtlas(3)));
        tabbedPane.addTab("Trees", createScrollPane(AssetPool.getLandscapeAtlas(4)));
        tabbedPane.addTab("Wild Flowers", createScrollPane(AssetPool.getLandscapeAtlas(5)));

        tabbedPane.setSelectedIndex(0);
        activeTilePanelIndex = tabbedPane.getSelectedIndex();

        tabbedPane.addChangeListener((ChangeEvent e) -> {
            activeTilePanelIndex = tabbedPane.getSelectedIndex();
            activeTilePanelIndex = activeTilePanelIndex != -1 ? activeTilePanelIndex : tileParser.invalidAtlasIndex;
        });

        editorPanel.add(layerPanel, constraints);
        constraints.insets.bottom = 0;
        constraints.gridy = 1;
        editorPanel.add(tabbedPane, constraints);

        return editorPanel;
    }

    public TilePanel getTilePanel(int panelIndex) {
        return tilePanels.get(panelIndex);
    }

    //TODO: Implement layer logic
    public int getCurrentLayer() {
        return 0;
    }

    public void setCurrentLayer(int layer) {

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

