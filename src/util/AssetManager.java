package util;

import game.level.Block;
import game.level.Level;
import game.level.dynamicobject.enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public abstract class AssetManager {
    private static HashMap<String, BufferedImage> textures = new HashMap<>();
    private static BufferedImage defaultImage;

    private static final String TEXTURES_PATH = "assets/textures/";

    static {
        try {
            defaultImage = ImageIO.read(new File(TEXTURES_PATH + "default.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage getTexture(String path) {
        File textureFile = new File(TEXTURES_PATH + path);

        if (textures.containsKey(textureFile.getAbsolutePath())) {
            return textures.get(textureFile.getAbsolutePath());
        } else {
            try {
                textures.put(textureFile.getAbsolutePath(), ImageIO.read(textureFile));
                return textures.get(textureFile.getAbsolutePath());
            } catch (IOException e) {
                return defaultImage;
            }
        }
    }

    public static Level getLevel(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Level level = (Level) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return level;
    }

    public static Enemy getEnemy(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Enemy enemy = (Enemy) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return enemy;
    }

    public static Block getBlock(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Block block = (Block) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return block;
    }
}
