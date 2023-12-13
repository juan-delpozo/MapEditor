import java.awt.Graphics;

public abstract class Layer {
    public boolean contentVisible;
    private int[][] serializedGrid;

    public Layer(int numRows, int numCols) {
        contentVisible = true;
        serializedGrid = new int[numRows][numCols];
    }
    
    public int getSerialized(int row, int col) {
        return serializedGrid[row][col];
    }

    public void setSerialized(int row, int col, int code) {
        serializedGrid[row][col] = code;
        update(row, col, code);
    }

    public boolean isContentVisible() {
        return contentVisible;
    }
    
    public void draw(Graphics g, int startRow, int startCol, int endRow, int endCol, int tileWidth, int tileHeight) {
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                draw(g, row, col, tileWidth, tileHeight);
            }
        }
    };

    public abstract void update(int row, int col, int code);
    protected abstract void draw(Graphics g, int row, int col, int tileWidth, int tileHeight);
}
