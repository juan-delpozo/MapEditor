import javax.swing.JPanel;

public class Layer extends JPanel {
    public boolean contentVisible;
    private Tile[][] tileGrid;
    private int[][] serializedGrid;

    public Layer(int numRows, int numCols) {
        contentVisible = true;
        tileGrid = new Tile[numRows][numCols];
        serializedGrid = new int[numRows][numCols];
    }

    
    public int getSerialized(int row, int col) {
        return serializedGrid[row][col];
    }

    public void setSerialized(int row, int col, int code) {
        serializedGrid[row][col] = code;
    }

    public Tile getTile(int row, int col) {
        return tileGrid[row][col];
    }

    public void setTile(int row, int col, Tile tile) {
        tileGrid[row][col] = tile;
    }
}
