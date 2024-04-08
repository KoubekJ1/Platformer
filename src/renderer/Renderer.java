package renderer;

import game.level.Block;
import game.level.Camera;
import game.level.Player;
import game.level.enemy.Enemy;
import renderer.window.WindowManager;

import java.awt.*;
import java.util.LinkedList;

public abstract class Renderer {

    private static Graphics2D g2D;
    private static float baseBlockSize;

    public static void setGraphics(Graphics2D graphics) {
        Renderer.g2D = graphics;
        updateBaseBlockSize();
    }

    public static void render(Block[][] blocks, Player[] players, Camera camera, LinkedList<Enemy> enemies, Color background) {
        updateBaseBlockSize();
        g2D.setTransform(camera.getTransform());

    }

    private static void updateBaseBlockSize() {
        baseBlockSize = WindowManager.getResolution()[0] / 60.0f;
    }

}
