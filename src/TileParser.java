public class TileParser {
    private static final double inverseLog2 = 1.0 / Math.log(2);
    private static int getNecessayBits(int value) {
        double logValue = Math.log(value);
        return (int) Math.ceil(logValue * inverseLog2);
    } 

    public TileAtlas[] tileAtlases;
    private int tileBits;
    private int tileMask;
    private int atlasBits;
    private int atlasMask;
    private String formatString;
    public int invalidTileIndex;
    public int invalidAtlasIndex;

    public TileParser(TileAtlas[] atlases) {
        tileAtlases = atlases;
        int maxTiles = 0;
        int numAtlases = atlases.length;
        invalidAtlasIndex = numAtlases;
        for (int i = 0; i < numAtlases; i++) {
            TileAtlas atlas = atlases[i];
            int numTiles = atlas.getNumTiles();
            maxTiles = numTiles > maxTiles ? numTiles : maxTiles;
        }
        invalidTileIndex = maxTiles;

        tileBits = getNecessayBits(maxTiles);
        tileMask = (int) Math.pow(2, tileBits) - 1;
        atlasBits = getNecessayBits(numAtlases);
        atlasMask = ((int) Math.pow(2 , atlasBits) - 1) << tileBits;
        formatString = "%" + (tileBits + atlasBits) + "s";
    }

    public int getCode(int tileAtlasIndex, int tileIndex) {
        int code = tileAtlasIndex << tileBits;
        code = code | tileIndex; // Bitwise or to store tileAtlas and index into one number code.

        return code;
    }

    public int parseString(String code) {
        return Integer.parseInt(code, 2);
    }

    public String getString(int code) {
        return String.format(formatString, Integer.toString(code, 2)).replace(" ", "0");
    }

    public Tile getTile(int code) {
        int tileAtlasIndex = (code & atlasMask) >> tileBits; // Bitwise and to get tileAtlasIndex from code
        int tileIndex = code & tileMask; // Bitwise and to get tileIndex from code
        
        if (tileAtlasIndex == invalidAtlasIndex || tileIndex == invalidTileIndex) {
            return null;
        }

        TileAtlas atlas = tileAtlases[tileAtlasIndex];
        return atlas.getTile(tileIndex);
    }

    public TileAtlas[] getTileAtlases() {
        return tileAtlases;
    }
}
