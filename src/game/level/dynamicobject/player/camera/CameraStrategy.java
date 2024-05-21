package game.level.dynamicobject.player.camera;

import java.io.Serializable;

public abstract class CameraStrategy implements Serializable {

    protected Camera parentCamera;

    public CameraStrategy(Camera parentCamera) {
        this.parentCamera = parentCamera;
    }

    public abstract void update();
}
