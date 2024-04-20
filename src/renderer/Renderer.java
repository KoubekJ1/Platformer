package renderer;

import game.level.Block;
import game.level.Camera;
import game.level.Player;
import game.level.enemy.Enemy;
import renderer.window.GameplayJPanel;
import renderer.window.WindowManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public abstract class Renderer {

    //private static Graphics2D g2D;
    private static GameplayJPanel panel;
    private static float baseBlockSize;


    public static void setPanel(GameplayJPanel panel) {
        if (panel == null) {
            throw new IllegalArgumentException("Panel is null!");
        }
        Renderer.panel = panel;
        //updateBaseBlockSize();
    }

    public static void render(RenderInfo renderInfo) {
        panel.render(renderInfo);
    }

   /* private static void updateBaseBlockSize() {
        baseBlockSize = WindowManager.getResolution()[0] / 60.0f;
    }*/

}
