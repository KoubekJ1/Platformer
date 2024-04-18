package game.level;

import game.level.enemy.Enemy;
import renderer.Renderer;
import renderer.window.WindowManager;

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

    public Level(String levelID, String levelName) {
        this.levelID = levelID;
        this.levelName = levelName;
        this.players = new LinkedList<>();
        players.add(new Player());
        this.blocks = new Block[0][];
        this.enemies = new LinkedList<>();
        this.gameTimer = new Timer(1000/WindowManager.getRefreshRate(), this);
        this.gameTimer.setInitialDelay(0);
    }

    public void start() {
        gameTimer.start();
    }

    public void update() {
        Renderer.render(blocks, players, players.getFirst().getCamera(), enemies, Color.CYAN);
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
        }
    }
}
