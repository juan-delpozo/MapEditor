import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TileMapEditor extends JFrame implements ActionListener {

    public TilePanel tilePN;
    public MapPanel mapPN;
    public JButton saveBN;
    public TileAtlas tileAtlas;
    public  TileMap tilemap;

    public TileMapEditor() {
        try {
            BufferedImage grounds = ImageIO.read(new File("res/sprites/landscape/FG_Grounds.png"));
         
            // First sample area
            Rect grass = new Rect(96, 192, 90, 48);

            // Second sample area
            Rect dirt = new Rect(96, 576, 90, 48);


            // Create TileAtlas with both sample areas
            int[][] tileDimensions = {
                    {16, 16, 0}, // Specify the tile dimensions and spacing for sampleArea1
                    {16, 16, 0}  // Specify the tile dimensions and spacing for sampleArea2
            };
            tileAtlas = new TileAtlas(grounds, new Rect[]{grass, dirt}, tileDimensions);

            System.out.println(tileAtlas.getNumTiles());

            // You need to adjust code based on how many tiles. The num of tiles is printed to the console.
            tilemap = new TileMap("map.map", tileAtlas, 16, "abcdefghijklmnopqrstuvwxyzABCDEFG");

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1280, 900);
            setTitle("Tile Map Editor");

            tilePN = new TilePanel(this);
            mapPN = new MapPanel(this);
            saveBN = new JButton("Save");

            setLayout(null);

            add(mapPN);
            add(tilePN);
            add(saveBN);

            saveBN.setBounds(10, 500, 100, 20);

            saveBN.addActionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBN) {
            tilemap.saveMap("map.map");
            System.out.println("Saved map to bin folder.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TileMapEditor editor = new TileMapEditor();
            editor.setVisible(true);
        });
    }
}

