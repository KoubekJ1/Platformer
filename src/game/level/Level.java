package game.level;

import game.level.enemy.Enemy;

import java.io.*;
import java.util.LinkedList;

public class Level implements Serializable {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    private String levelID;
    private String levelName;

    private Player player;
    private Block[][] blocks;
    private LinkedList<Enemy> enemies;

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(LEVELS_DIRECTORY + "custom/" + levelID + ".level");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
