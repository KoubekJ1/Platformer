package game.level;

import game.ProgramManager;
import game.level.character.DynamicObject;
import game.level.character.player.powerups.Powerup;
import game.level.character.enemy.Enemy;
import game.level.character.player.Player;
import renderer.RenderInfo;
import renderer.Renderer;
import renderer.window.WindowManager;
import util.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class Level implements Serializable/*, ActionListener*/ {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    private String levelID;
    private String levelName;

    private LinkedList<DynamicObject> dynamicObjects;
    private LinkedList<DynamicObject> dynamicObjectsForRemoval;

    private LinkedList<Player> players;
    private Block[][] blocks;
    private LinkedList<Enemy> enemies;
    private LinkedList<Powerup> powerups;

    //private Timer gameTimer;
    private UpdateThread updateThread;

    private RenderInfo renderInfo;

    /*float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = 0;*/

    public Level(String levelID, String levelName, int sizeX, int sizeY) {
        this.levelID = levelID;
        this.levelName = levelName;

        this.dynamicObjects = new LinkedList<>();
        this.dynamicObjectsForRemoval = new LinkedList<>();

        this.players = new LinkedList<>();
        addObject(new Player());
        this.blocks = new Block[sizeX][sizeY];
        this.enemies = new LinkedList<>();
        this.powerups = new LinkedList<>();

        // Setting the timer delay to half of the refresh rate seems to produce the expected amount of frames per second, weird fix
        //this.gameTimer = new Timer(500/WindowManager.getRefreshRate(), this);
        //this.gameTimer.setInitialDelay(0);

        this.updateThread = new UpdateThread();
    }

    public void addBlock(Block block, int x, int y) {
        blocks[x][y] = block;
    }

    public void addObject(DynamicObject object) {
        this.dynamicObjects.add(object);
        getCorrespondingDynamicObjectLinkedList(object).add(object);
    }

    public void removeObject(DynamicObject object) {
        dynamicObjectsForRemoval.add(object);
    }

    private LinkedList getCorrespondingDynamicObjectLinkedList(DynamicObject object) {
        String objectClass[] = object.getClass().toString().split("\\.");
        return switch (objectClass[objectClass.length - 1]) {
            case "Player" -> players;
            case "Enemy" -> enemies;
            case "Powerup" -> powerups;
            default -> throw new IllegalArgumentException("Invalid object!");
        };
    }

    public void start() {
        //gameTimer.start();
        updateThread.start();
    }

    public void stop() {
        //gameTimer.stop();
        updateThread.interrupt();
    }

    public void update(float dt) {
        //LinkedList<DynamicObject> dynamicObjects = (LinkedList<DynamicObject>) Stream.concat(Stream.concat(players.stream(), enemies.stream()), powerups.stream()).toList();
        renderInfo = new RenderInfo(Color.CYAN, players.getFirst().getCamera(), blocks, dynamicObjects);
        if (ProgramManager.isDebug()) {
            renderInfo.setFrameRate(1/dt);
        }
        for (DynamicObject object : dynamicObjects) {
            object.update(dt);
        }
        Renderer.render(renderInfo);

        for (DynamicObject dynamicObject : dynamicObjectsForRemoval) {
            this.dynamicObjects.remove(dynamicObject);
            getCorrespondingDynamicObjectLinkedList(dynamicObject).remove(dynamicObject);
        }

        /*endTime = Time.getTime();
        dt = endTime - beginTime;
        beginTime = endTime;*/
    }

    public Block getBlock(int x, int y) {
        return blocks[x][y];
    }

    public int getLevelSizeX() {
        return blocks.length;
    }

    public int getLevelSizeY() {
        return blocks[0].length;
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

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(LEVELS_DIRECTORY + "custom/" + levelID + ".level");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
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
