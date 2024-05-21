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

public class Level implements Serializable/*, ActionListener*/ {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    public static final Color SKY_COLOR = new Color(135, 206, 235);
    public static final Color UNDERGROUND_COLOR = new Color(3, 30, 46);

    private String levelID;
    private String levelName;
    private ImageIcon icon;

    private LinkedList<DynamicObject> dynamicObjects;
    private LinkedList<DynamicObject> dynamicObjectsToAdd;
    private LinkedList<DynamicObject> dynamicObjectsForRemoval;

    private LinkedList<Player> players;
    private Block[][] blocks;
    private LinkedList<Enemy> enemies;
    private LinkedList<Powerup> powerups;
    private LinkedList<Projectile> projectiles;
    private LinkedList<Finish> finishPoints;

    private Color background;

    private Score score;

    private float fps;

    //private Timer gameTimer;
    private UpdateThread updateThread;

    /*float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = 0;*/

    public Level(String levelID, String levelName, ImageIcon imageIcon, int sizeX, int sizeY, Color background, int playerX, int playerY) {
        this.levelID = levelID;
        this.levelName = levelName;
        this.icon = imageIcon;

        this.dynamicObjects = new LinkedList<>();
        this.dynamicObjectsToAdd = new LinkedList<>();
        this.dynamicObjectsForRemoval = new LinkedList<>();

        this.players = new LinkedList<>();
        addObject(new Player(playerX, playerY));
        this.blocks = new Block[sizeX][sizeY];
        this.enemies = new LinkedList<>();
        this.powerups = new LinkedList<>();
        this.projectiles = new LinkedList<>();
        this.finishPoints = new LinkedList<>();

        this.score = new Score();

        this.background = background;

        this.updateThread = new UpdateThread();
    }

    public void addBlock(Block block, int x, int y) {
        blocks[x][y] = block;
    }

    public void addObject(DynamicObject object) {
        if (dynamicObjects.contains(object)) return;
        dynamicObjectsToAdd.add(object);
    }

    public void removeObject(DynamicObject object) {
        if (!dynamicObjects.contains(object)) return;
        dynamicObjectsForRemoval.add(object);
    }

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

    public void start() {
        //gameTimer.start();
        updateThread.start();
    }

    public void stop() {
        //gameTimer.stop();
        updateThread.interrupt();
        InputManager.resetInputs();
    }

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
                    enemy.damage();
                    removeObject(projectile);
                }
            }
        }

        this.fps = 1/dt;
        Renderer.render();
    }

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

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(LEVELS_DIRECTORY + "custom/" + levelID + ".level");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Override
    public String toString() {
        return "Level{" +
                "levelID='" + levelID + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            update();
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }*/
}
