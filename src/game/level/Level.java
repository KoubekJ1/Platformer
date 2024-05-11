package game.level;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.Projectile;
import game.level.dynamicobject.player.powerups.Powerup;
import game.level.dynamicobject.enemy.Enemy;
import game.level.dynamicobject.player.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;

public class Level implements Serializable/*, ActionListener*/ {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    public static final Color SKY_COLOR = new Color(135, 206, 235);

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

    private Color background;

    private long score;

    //private Timer gameTimer;
    private UpdateThread updateThread;

    private RenderInfo renderInfo;

    /*float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = 0;*/

    public Level(String levelID, String levelName, int sizeX, int sizeY, Color background) {
        this.levelID = levelID;
        this.levelName = levelName;

        this.dynamicObjects = new LinkedList<>();
        this.dynamicObjectsToAdd = new LinkedList<>();
        this.dynamicObjectsForRemoval = new LinkedList<>();

        this.players = new LinkedList<>();
        addObject(new Player());
        this.blocks = new Block[sizeX][sizeY];
        this.enemies = new LinkedList<>();
        this.powerups = new LinkedList<>();
        this.projectiles = new LinkedList<>();

        this.score = 0;

        this.background = background;

        this.updateThread = new UpdateThread();
    }

    public void addBlock(Block block, int x, int y) {
        blocks[x][y] = block;
    }

    public void addObject(DynamicObject object) {
        dynamicObjectsToAdd.add(object);
        //this.dynamicObjects.add(object);
        //getCorrespondingDynamicObjectLinkedList(object).add(object);
    }

    public void removeObject(DynamicObject object) {
        dynamicObjectsForRemoval.add(object);
    }

    private LinkedList getCorrespondingDynamicObjectLinkedList(DynamicObject object) {
        return switch (object.getObjectCategory()) {
            case "player" -> players;
            case "enemy" -> enemies;
            case "powerup" -> powerups;
            case "projectile" -> projectiles;
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


        renderInfo = new RenderInfo(background, players.getFirst().getCamera(), blocks, dynamicObjects);
        if (ProgramManager.isDebug()) {
            renderInfo.setFrameRate(1/dt);
        }
        Renderer.render(renderInfo);
    }

    public void addScore(int score) {
        this.score += score;
    }

    public long getScore() {
        return score;
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
