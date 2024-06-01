package game.level.dynamicobject.player.camera;

import game.level.dynamicobject.player.Player;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * Camera instances contain a 3x3 matrix in the form of an AffineTransform that defines where the virtual camera is and the scale of the world
 * The AffineTransform is applied to the Graphics2D instance when rendering
 * Further behaviour of the camera is defined by its strategy
 */
public class Camera implements Serializable {
    private AffineTransform transform;
    private Player parentPlayer;
    private CameraStrategy cameraStrategy;

    /**
     * Creates a new camera without a strategy
     */
    public Camera() {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
    }

    /**
     * Creates a camera with the follow player strategy
     * @param parentPlayer the player to follow
     */
    public Camera(Player parentPlayer) {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
        this.parentPlayer = parentPlayer;
        this.cameraStrategy = new FollowPlayerStrategy(this);
    }

    /**
     * Creates a camera with the given strategy that takes the given player into account
     * @param parentPlayer the player
     * @param cameraStrategy the strategy
     */
    public Camera(Player parentPlayer, CameraStrategy cameraStrategy) {
        transform = new AffineTransform(1, 0, 0, 1, 0, 0); // Base scale, upper-left corner position
        this.parentPlayer = parentPlayer;
        this.cameraStrategy = cameraStrategy;
    }

    /**
     * Sets the matrix's translation aspect, which serves as the camera's position
     * @param x the x-coordinate in game units
     * @param y the y-coordinate in game units
     */
    public void setPosition(double x, double y) {
        transform.setTransform(transform.getScaleX(), 0, 0, transform.getScaleY(), x, y);
    }

    /**
     * Sets the scale of the displayed world
     * @param scale the scale
     */
    public void setWorldScale(double scale) {
        transform.setTransform(scale, 0, 0, scale, transform.getTranslateX(), transform.getTranslateY());
    }

    /**
     * Updates the camera using its strategy
     */
    public void update() {
        if (cameraStrategy == null) return;
        cameraStrategy.update();
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public Player getParentPlayer() {
        return parentPlayer;
    }
}
