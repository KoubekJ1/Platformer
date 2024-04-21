package game.level.player.camera;

import game.level.player.Player;

import java.awt.geom.AffineTransform;

public class Camera {
    private AffineTransform transform;
    private Player parentPlayer;

    public Camera() {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
    }

    public Camera(Player parentPlayer) {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
        this.parentPlayer = parentPlayer;
    }

    public void setPosition(double x, double y) {
        transform = new AffineTransform(transform.getScaleX(), 0, 0, transform.getScaleY(), -x, -y);
    }

    public void setWorldScale(double scale) {
        // Weird workaround, the scale() method doesn't save translation, translation is applied again after scaling
        transform = new AffineTransform(scale, 0, 0, scale, transform.getTranslateX(), transform.getTranslateY());
    }

    public void update() {
        if (parentPlayer == null) return;

        setPosition(parentPlayer.getPosition()[0], parentPlayer.getPosition()[1]);
    }

    public AffineTransform getTransform() {
        return transform;
    }
}
