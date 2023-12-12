import java.io.IOException;

/**
 * A class which stores all assets of the game. Any asset that needs to be used,
 * should be obtained from this class.
 */
public final class AssetPool {
    private static TileAtlas[] entityAtlases;
    private static TileAtlas[] objectAtlases;
    private static TileAtlas[] landscapeAtlases;
    private static boolean loaded = false;

    public static void load() {
        if (loaded) {
            return;
        }

        entityAtlases = new TileAtlas[5];
        objectAtlases = new TileAtlas[5];
        landscapeAtlases = new TileAtlas[6];

        try {
            loadAtlases();
            createAnimationSets();
            loaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all assets in the res folder as TileAtlases.
     * 
     * @throws IOException
     */
    private static void loadAtlases() throws IOException {
        String entitiesFolder = "./res/sprites/entities/";
        String objectsFolder = "./res/sprites/objects/";
        String landscapeFolder = "./res/sprites/landscape/";

        entityAtlases[0] = new TileAtlas(entitiesFolder + "character01.png", 32, 32, 0);
        entityAtlases[1] = new TileAtlas(entitiesFolder + "Character05.png", 32, 32, 0);
        entityAtlases[2] = new TileAtlas(entitiesFolder + "character10.png", 32, 32, 0);
        entityAtlases[3] = new TileAtlas(entitiesFolder + "Knight_10.png", 32, 32, 0);
        entityAtlases[4] = new TileAtlas(entitiesFolder + "Knight_11.png", 32, 32, 0);

        objectAtlases[0] = new TileAtlas(objectsFolder + "FG_Crystals.png", 16, 16, 0);
        objectAtlases[1] = new TileAtlas(objectsFolder + "FG_Rocks.png", 16, 16, 0);

        objectAtlases[2] = new TileAtlas(objectsFolder + "FG_Signs.png", 16, 16, 0);
        objectAtlases[3] = new TileAtlas(objectsFolder + "FG_Treasure.png", 16, 16, 0);
        objectAtlases[4] = new TileAtlas(objectsFolder + "FG_Grass.png", 16, 16, 0);

        landscapeAtlases[0] = new TileAtlas(landscapeFolder + "FG_Fences.png", 16, 16, 0);
        landscapeAtlases[1] = new TileAtlas(landscapeFolder + "FG_Grounds.png", 16, 16, 0);
        landscapeAtlases[2] = new TileAtlas(landscapeFolder + "FG_Logs.png", 16, 16, 0);
        landscapeAtlases[3] = new TileAtlas(landscapeFolder + "FG_Mushrooms.png", 16, 16, 0);
        landscapeAtlases[4] = new TileAtlas(landscapeFolder + "FG_Trees.png", 16, 16, 0);
        landscapeAtlases[5] = new TileAtlas(landscapeFolder + "FG_Wild_Flowers.png", 16, 16, 0);
    }

    /**
     * Create entity animation sets
     */
    private static void createAnimationSets() {

    }

    public static TileAtlas[] getEntityAtlases() {
        return entityAtlases;
    }

    public static TileAtlas getEntityAtlas(int index) {
        return entityAtlases[index];
    }

    public static TileAtlas[] getObjectAtlases() {
        return objectAtlases;
    }

    public static TileAtlas getObjectAtlas(int index) {
        return objectAtlases[index];
    }

    public static TileAtlas[] getLandscapeAtlases() {
        return landscapeAtlases;
    }

    public static TileAtlas getLandscapeAtlas(int index) {
        return landscapeAtlases[index];
    }

    public static boolean isLoaded() {
        return loaded;
    }
}