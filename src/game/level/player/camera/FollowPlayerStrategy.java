package game.level.player.camera;

public class FollowPlayerStrategy extends CameraStrategy {
    public FollowPlayerStrategy(Camera parentCamera) {
        super(parentCamera);
    }

    @Override
    public void update() {
        parentCamera.setPosition(parentCamera.getParentPlayer().getPosition()[0], parentCamera.getParentPlayer().getPosition()[1]);
    }
}
