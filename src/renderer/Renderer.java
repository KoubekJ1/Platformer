package renderer;

import game.level.Block;
import game.level.Camera;
import game.level.Player;
import game.level.enemy.Enemy;
import renderer.window.WindowManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public abstract class Renderer {

    private static Graphics2D g2D;
    private static float baseBlockSize;

    private static AffineTransform defaultTransform = new AffineTransform(1, 0, 0, 1, 0, 0);

    public static void setGraphics(Graphics2D graphics) {
        Renderer.g2D = graphics;
        updateBaseBlockSize();
    }

    public static void render(Block[][] blocks, LinkedList<Player> players, Camera camera, LinkedList<Enemy> enemies, Color background) {
        // Reset transform
        g2D.setTransform(defaultTransform);

        // Render background
        g2D.setColor(background);
        g2D.fillRect(0, 0, WindowManager.getResolution()[0], WindowManager.getResolution()[1]);

        // Setup
        updateBaseBlockSize();
        g2D.setTransform(camera.getTransform());

        // Rendering the player(s)
        for (Player player : players) {
            g2D.drawImage(player.getCurrentImage(baseBlockSize), (int) (player.getPosition()[0] * baseBlockSize), (int) (player.getPosition()[1] * baseBlockSize), null, null);
        }
    }

    private static void updateBaseBlockSize() {
        baseBlockSize = WindowManager.getResolution()[0] / 60.0f;
    }

}
