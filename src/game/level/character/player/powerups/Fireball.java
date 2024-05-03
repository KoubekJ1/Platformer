package game.level.character.player.powerups;

import game.ProgramManager;
import game.level.character.DynamicObject;

public class Fireball extends DynamicObject {

    private static final float SPEED  = 5;
    private static final float JUMP_VELOCITY  = 1;

    @Override
    protected void objectUpdate(float dt) {
        if (velocityY == 0) jump();
        velocityX = SPEED * dt;
    }

    private void jump() {
        velocityY = JUMP_VELOCITY;
    }

    @Override
    protected void damage() {
        kill();
    }

    @Override
    public void kill() {
        ProgramManager.getLevel().removeObject(this);
    }

    @Override
    protected String getAssetDirectory() {
        return null;
    }

    @Override
    protected String getAssetExtension() {
        return null;
    }
}
