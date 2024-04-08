package game.level;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Camera {
    private AffineTransform transform;

    public Camera() {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
    }

    public void setPosition(int x, int y) {
        transform.translate(x, y);
    }

    public void setPosition(Point point) {
        setPosition(point.x, point.y);
    }

    public void setWorldScale(int scale) {
        // Weird workaround, the scale() method doesn't save translation, translation is applied again after scaling
        int[] translation = new int[]{(int) transform.getTranslateX(), (int) transform.getTranslateY()};
        transform.scale(scale, scale);
        setPosition(translation[0], translation[1]);
    }
}
