package game.level;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Camera {
    private AffineTransform transform;

    public Camera() {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
    }

    public void setPosition(double x, double y) {
        transform.translate(-x, -y);
    }

    public void setWorldScale(int scale) {
        // Weird workaround, the scale() method doesn't save translation, translation is applied again after scaling
        double[] translation = new double[]{transform.getTranslateX(), transform.getTranslateY()};
        transform.scale(scale, scale);
        setPosition(translation[0], translation[1]);
    }

    public AffineTransform getTransform() {
        return transform;
    }
}
