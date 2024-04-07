package renderer;

import java.awt.*;

public abstract class Renderer {

    private static Graphics2D g2D;

    public static void setGraphics(Graphics2D graphics) {
        Renderer.g2D = graphics;
    }

}
