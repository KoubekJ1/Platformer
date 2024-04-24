package renderer;

import game.level.Block;
import game.level.character.player.camera.Camera;
import game.level.character.player.Player;
import game.level.character.enemy.Enemy;

import java.awt.*;
import java.util.LinkedList;

public class RenderInfo {
    private Color background;
    private Camera camera;
    private Block[][] blocks;
    private LinkedList<Player> players;
    private LinkedList<Enemy> enemies;

    private float frameRate = 0;

    public RenderInfo(Color background, Camera camera, Block[][] blocks, LinkedList<Player> players, LinkedList<Enemy> enemies) {
        this.background = background;
        this.camera = camera;
        this.blocks = blocks;
        this.players = players;
        this.enemies = enemies;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(LinkedList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }
}
