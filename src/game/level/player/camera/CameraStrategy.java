package game.level.player.camera;

public abstract class CameraStrategy {

    protected Camera parentCamera;

    public CameraStrategy(Camera parentCamera) {
        this.parentCamera = parentCamera;
    }

    public abstract void update();
}
