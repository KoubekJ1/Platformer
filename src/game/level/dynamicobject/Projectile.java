package game.level.dynamicobject;

import game.ProgramManager;
import renderer.Sprite;

public abstract class Projectile extends DynamicObject {

    private float speed;
    private float jumpVelocity;
    private boolean bounces;
    private boolean direction;

    public Projectile(float x, float y, float speed, float jumpVelocity, boolean jumpsOnSpawn, boolean bounces, int direction) {
        this.setPosX(x);
        this.setPosY(y);
        this.speed = speed;
        this.jumpVelocity = jumpVelocity;
        this.bounces = bounces;

        if (direction == -1) this.direction = false;
        else if (direction == 1) this.direction = true;
        else throw new IllegalArgumentException("Direction must be 1 or -1");

        sprite = getProjectileSprite();
        sprite.playAnimation("move");

        if (jumpsOnSpawn) {
            jump();
        }
    }

    public Projectile(float x, float y, float speed, float jumpVelocity, boolean jumpsOnSpawn, boolean bounces, boolean direction) {
        this.setPosX(x);
        this.setPosY(y);
        this.speed = speed;
        this.jumpVelocity = jumpVelocity;
        this.bounces = bounces;
        this.direction = direction;

        sprite = getProjectileSprite();
        sprite.playAnimation("move");

        if (jumpsOnSpawn) {
            jump();
        }
    }

    protected abstract Sprite getProjectileSprite();

    protected int getDirection() {
        if (direction == true) return 1;
        return -1;
    }

    @Override
    protected void objectUpdate(float dt) {
        if (bounces && velocityY == 0) jump();
        velocityX = speed * getDirection() * dt;
        if (leftBlockCollisionCheck()) kill();
        if (rightBlockCollisionCheck()) kill();
    }

    private void jump() {
        velocityY = -jumpVelocity;
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
    public String getObjectCategory() {
        return "projectile";
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
