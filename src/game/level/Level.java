package game.level;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.Finish;
import game.level.dynamicobject.Projectile;
import game.level.dynamicobject.player.powerups.Powerup;
import game.level.dynamicobject.enemy.Enemy;
import game.level.dynamicobject.player.Player;
import renderer.Renderer;
import util.InputManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

/**
 * Instances of the Level class represent the individual game worlds that the player navigates through
 */
public class Level implements Serializable/*, ActionListener*/ {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    public static final Color SKY_COLOR = new Color(135, 206, 235);
    public static final Color UNDERGROUND_COLOR = new Color(3, 30, 46);

    private String levelID;
    private String levelName;

    private LinkedList<DynamicObject> dynamicObjects;
    private LinkedList<DynamicObject> dynamicObjectsToAdd;
    private LinkedList<DynamicObject> dynamicObjectsForRemoval;

    private LinkedList<Player> players;
    private Block[][] blocks;
    private LinkedList<Enemy> enemies;
    private LinkedList<Powerup> powerups;
    private LinkedList<Projectile> projectiles;
    private LinkedList<Finish> finishPoints;

    private Point playerSpawn;

    private Color background;

    private Score score;

    private float fps;

    private UpdateThread updateThread;

    /**
     * Creates a new level with the given parameters
     * @param levelID the id of the level (used for serialization)
     * @param levelName the level's name
     * @param sizeX the size of the level on the x-axis
     * @param sizeY the size of the level on the y-axis
     * @param background the color of the background
     * @param playerX the player's spawn x-coordinate
     * @param playerY the player's spawn y-coordinate
     * */
    public Level(String levelID, String levelName, int sizeX, int sizeY, Color background, int playerX, int playerY) {
        this.levelID = levelID;
        this.levelName = levelName;

        this.dynamicObjects = new LinkedList<>();
        this.dynamicObjectsToAdd = new LinkedList<>();
        this.dynamicObjectsForRemoval = new LinkedList<>();

        this.players = new LinkedList<>();
        playerSpawn = new Point(playerX, playerY);
        this.blocks = new Block[sizeX][sizeY];
        this.enemies = new LinkedList<>();
        this.powerups = new LinkedList<>();
        this.projectiles = new LinkedList<>();
        this.finishPoints = new LinkedList<>();

        this.score = new Score();

        this.background = background;

        this.updateThread = new UpdateThread();
    }

    /**
     * Puts the given block in the given coordinates
     * @param block the block
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void addBlock(Block block, int x, int y) {
        blocks[x][y] = block;
    }

    /**
     * Adds the dynamicObject to the level and puts it in its category for optimization purposes
     * @param object the object to be added
     */
    public void addObject(DynamicObject object) {
        if (dynamicObjects.contains(object)) return;
        dynamicObjectsToAdd.add(object);
    }

    /**
     * Removes the object from the level
     * @param object the object to be removed
     */
    public void removeObject(DynamicObject object) {
        if (!dynamicObjects.contains(object)) return;
        dynamicObjectsForRemoval.add(object);
    }

    /**
     * Gets the linked list that the object belongs in based on its class
     * @param object the object
     * @return the linked list
     */
    private LinkedList getCorrespondingDynamicObjectLinkedList(DynamicObject object) {
        return switch (object.getObjectCategory()) {
            case "player" -> players;
            case "enemy" -> enemies;
            case "powerup" -> powerups;
            case "projectile" -> projectiles;
            case "finish" -> finishPoints;
            default -> throw new IllegalArgumentException("Invalid object: " + object.getObjectCategory());
        };
    }

    /**
     * Starts the game loop
     */
    public void start() {
        addObject(new Player(playerSpawn.x, playerSpawn.y));
        updateThread.start();
    }

    /**
     * Pauses the game
     */
    public void pause() {
        updateThread.pause();
    }

    /**
     * Unpauses the game
     */
    public void resume() {
        updateThread.unpause();
    }

    /**
     * Ends the level
     */
    public void stop() {
        updateThread.interrupt();
        InputManager.resetInputs();
    }

    /**
     * Handles the level's update loop
     * @param dt the time between update() calls in seconds
     */
    public void update(float dt) {
        for (DynamicObject object : dynamicObjects) {
            object.update(dt);
        }

        for (DynamicObject dynamicObject : dynamicObjectsToAdd) {
            this.dynamicObjects.add(dynamicObject);
            getCorrespondingDynamicObjectLinkedList(dynamicObject).add(dynamicObject);
        }

        for (DynamicObject dynamicObject : dynamicObjectsForRemoval) {
            this.dynamicObjects.remove(dynamicObject);
            getCorrespondingDynamicObjectLinkedList(dynamicObject).remove(dynamicObject);
        }

        dynamicObjectsToAdd.clear();
        dynamicObjectsForRemoval.clear();

        for (Projectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                if (projectile.collision(enemy)) {
                    enemy.kill();
                    score.addScore(200);
                    removeObject(projectile);
                }
            }
        }

        this.fps = 1/dt;
        Renderer.render();
    }

    /**
     * Ends the level victoriously
     */
    public void finish() {
        JOptionPane.showMessageDialog(null, "You finished the level!\nScore: " + score.getScore(), levelName, JOptionPane.PLAIN_MESSAGE);
        ProgramManager.endLevel();
        Thread.currentThread().stop();
    }

    public Score getScore() {
        return score;
    }

    public Block getBlock(int x, int y) {
        return blocks[x][y];
    }

    public void removeBlock(int x, int y) {
        blocks[x][y] = null;
    }

    public float getFps() {
        return fps;
    }

    public LinkedList<DynamicObject> getDynamicObjects() {
        return this.dynamicObjects;
    }

    public int getLevelSizeX() {
        return blocks.length;
    }

    public int getLevelSizeY() {
        return blocks[0].length;
    }

    public Color getBackground() {
        return background;
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public LinkedList<Powerup> getPowerups() {
        return powerups;
    }

    public LinkedList<Finish> getFinishPoints() {
        return finishPoints;
    }

    /**
     * Serializes the level into its given category
     * @param category the category
     * @throws IOException in case there's an error
     */
    public void serialize(String category) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(LEVELS_DIRECTORY + category + "/" + levelID + ".level");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public String getLevelName() {
        return levelName;
    }

    @Override
    public String toString() {
        return "Level{" +
                "levelID='" + levelID + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}
