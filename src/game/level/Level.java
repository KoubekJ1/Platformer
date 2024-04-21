package game.level;

import game.ProgramManager;
import game.level.enemy.Enemy;
import game.level.player.Player;
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

public class Level implements Serializable, ActionListener {
    private static final String LEVELS_DIRECTORY = "assets/levels/";

    private String levelID;
    private String levelName;

    private LinkedList<Player> players;
    private Block[][] blocks;
    private LinkedList<Enemy> enemies;

    private Timer gameTimer;

    private RenderInfo renderInfo;

    float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = 0;

    public Level(String levelID, String levelName, int sizeX, int sizeY) {
        this.levelID = levelID;
        this.levelName = levelName;
        this.players = new LinkedList<>();
        players.add(new Player());
        this.blocks = new Block[sizeX][sizeY];
        this.enemies = new LinkedList<>();

        // Setting the timer delay to half of the refresh rate seems to produce the expected amount of frames per second, weird fix
        this.gameTimer = new Timer(500/WindowManager.getRefreshRate(), this);
        this.gameTimer.setInitialDelay(0);
    }

    public void addBlock(Block block, int x, int y) {
        blocks[x][y] = block;
    }

    public void start() {
        gameTimer.start();
    }

    public void update() {
        renderInfo = new RenderInfo(Color.CYAN, players.getFirst().getCamera(), blocks, players, enemies);
        if (ProgramManager.isDebug()) {
            renderInfo.setFrameRate(1/dt);
        }
        for (Player player : players) {
            player.update(dt);
        }
        Renderer.render(renderInfo);
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTimer) {
            update();
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
