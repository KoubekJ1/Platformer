package renderer;

import renderer.window.WindowManager;

import java.awt.*;

public abstract class Renderer {

    private static Graphics2D g2D;
    private static float baseBlockSize;

    public static void setGraphics(Graphics2D graphics) {
        Renderer.g2D = graphics;
        updateBaseBlockSize();
    }

    private static void updateBaseBlockSize() {
        baseBlockSize = WindowManager.getResolution()[0] / 60.0f;
    }

}
