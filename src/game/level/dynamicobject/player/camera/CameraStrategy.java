package game.level.dynamicobject.player.camera;

import java.io.Serializable;

/**
 * CameraStrategy subclasses define how the given camera behaves
 */
public abstract class CameraStrategy implements Serializable {

    protected Camera parentCamera;

    public CameraStrategy(Camera parentCamera) {
        this.parentCamera = parentCamera;
    }

    /**
     * Updates the camera
     * This method defines how the camera behaves
     */
    public abstract void update();
}
