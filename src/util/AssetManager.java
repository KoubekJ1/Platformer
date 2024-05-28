package util;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.Level;
import game.level.dynamicobject.enemy.Enemy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * AssetManager serves as a way to access textures and serialized objects
 * To prevent loading the textures multiple times and slowing down the game, they are saved upon first being loaded
 */
public abstract class AssetManager {
    private static HashMap<String, BufferedImage> textures = new HashMap<>();
    private static BufferedImage defaultImage;
    private static boolean warning_showed = false;

    private static final String TEXTURES_PATH = "assets/textures/";
    private static final String LEVELS_PATH = "assets/levels/official";

    static {
        try {
            defaultImage = ImageIO.read(new File(TEXTURES_PATH + "default.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot load the default texture!\n\n" + e.getClass() + "\n" + e.getMessage() + "\n\nThe texture is most likely missing from your \"assets\" folder. Make sure you have installed the game correctly.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Returns the texture located on the selected path
     * @param path the texture's path within the "textures/" folder
     * @return the texture
     */
    public static BufferedImage getTexture(String path) {
        File textureFile = new File(TEXTURES_PATH + path);

        if (textures.containsKey(textureFile.getAbsolutePath())) {
            return textures.get(textureFile.getAbsolutePath());
        } else {
            try {
                textures.put(textureFile.getAbsolutePath(), ImageIO.read(textureFile));
                return textures.get(textureFile.getAbsolutePath());
            } catch (IOException e) {
                if (!warning_showed) {
                    warning_showed = true;
                    ProgramManager.pause();
                    JOptionPane.showMessageDialog(null,"A texture could not be loaded!\n" + path + "\n\n" + e.getClass() + "\n" + e.getMessage() + "\n\nThe texture is most likely missing from your \"assets\" folder. Make sure you have installed the game correctly.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                return defaultImage;
            }
        }
    }

    /**
     * Deserializes and returns the level on the selected path
     * @param path the level's path
     * @return the level
     * @throws IOException in case the file is missing or corrupted
     * @throws ClassNotFoundException in case the class could not be found
     */
    public static Level getLevel(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Level level = (Level) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return level;
    }

    /**
     * Deserializes and returns the enemy on the selected path
     * Unused
     * @param path the enemy's path
     * @return the enemy
     * @throws IOException in case the file is missing or corrupted
     * @throws ClassNotFoundException in case the class could not be found
     */
    public static Enemy getEnemy(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Enemy enemy = (Enemy) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return enemy;
    }

    /**
     * Deserializes and returns the block on the selected path
     * Unused
     * @param path the block's path
     * @return the block
     * @throws IOException in case the file is missing or corrupted
     * @throws ClassNotFoundException in case the class could not be found
     */
    public static Block getBlock(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Block block = (Block) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return block;
    }

    /**
     * Deserializes and returns a LinkedList of all levels in the assets/levels/official/ folder
     * @return the levels
     * @throws IOException in case the levels could not be loaded
     * @throws ClassNotFoundException in case the class could not be found
     */
    public static LinkedList<Level> getLevels() throws IOException, ClassNotFoundException {
        LinkedList<Level> levels = new LinkedList<>();

        File[] files = new File(LEVELS_PATH).listFiles();

        for (File file : files) {
            levels.add(getLevel(file.getPath()));
        }

        return levels;
    }
}
