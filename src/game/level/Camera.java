package game.level;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Camera {
    private AffineTransform transform;

    public Camera() {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
    }
}
