public class TileParser {

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

        tileBits = 11; // TODO: Programatically find min number of bits to store maxTiles
        tileMask = (int) Math.pow(2, tileBits) - 1;

        atlasBits = 3; // TODO: Programatically find min number of bits to store numAtlases
        atlasMask = ((int) Math.pow(2 , atlasBits) - 1) << tileBits;
        formatString = "%" + (tileBits + atlasBits) + "s";
        System.out.println(getString(tileMask));
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
