package game.level.dynamicobject.player.camera;

/**
 * Camera strategy that actively follows the player to keep him visible at all times
 */
public class FollowPlayerStrategy extends CameraStrategy {
    public FollowPlayerStrategy(Camera parentCamera) {
        super(parentCamera);
    }

    @Override
    public void update() {
        parentCamera.setPosition(-(parentCamera.getParentPlayer().getPosX() - 15), -(parentCamera.getParentPlayer().getPosY() - 15));
    }
}
