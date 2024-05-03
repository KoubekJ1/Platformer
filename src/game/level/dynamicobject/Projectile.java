package game.level.dynamicobject;

import game.ProgramManager;
import renderer.Sprite;

public abstract class Projectile extends DynamicObject {

    private float speed;
    private float jumpVelocity;
    private boolean bounces;

    public Projectile(float x, float y, float jumpVelocity, boolean jumpsOnSpawn, boolean bounces) {
        this.setPosX(x);
        this.setPosY(y);
        this.jumpVelocity = jumpVelocity;
        this.bounces = bounces;

        sprite = getProjectileSprite();
        sprite.playAnimation("move");

        if (jumpsOnSpawn) {
            jump();
        }
    }

    protected abstract Sprite getProjectileSprite();

    @Override
    protected void objectUpdate(float dt) {
        if ( velocityY == 0) jump();
        velocityX = speed * dt;
        if (leftBlockCollisionCheck()) kill();
        if (rightBlockCollisionCheck()) kill();
    }

    private void jump() {
        velocityY = jumpVelocity;
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
