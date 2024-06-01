package game.level.dynamicobject;

import game.ProgramManager;
import renderer.Sprite;

/**
 * Projectiles are objects that hurt enemies upon contact
 */
public abstract class Projectile extends DynamicObject {

    private float speed;
    private float jumpVelocity;
    private boolean bounces;
    private boolean direction;

    /**
     * Constructs a new projectile with the given parameters
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param speed the horizontal speed of the projectile
     * @param jumpVelocity the upwards velocity applied to the projectile upon "jumping"
     * @param jumpsOnSpawn whether the projectile jumps upon spawning
     * @param bounces whether the projectile bounces when it hits the ground
     * @param direction the direction of the projectile (1 - right, -1 - left)
     */
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

    /**
     * Constructs a new projectile with the given parameters
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param speed the horizontal speed of the projectile
     * @param jumpVelocity the upwards velocity applied to the projectile upon "jumping"
     * @param jumpsOnSpawn whether the projectile jumps upon spawning
     * @param bounces whether the projectile bounces when it hits the ground
     * @param direction the direction of the projectile (true - right, false - left)
     */
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

    /**
     * Returns the sprite used by the projectile
     * @return the sprite
     */
    protected abstract Sprite getProjectileSprite();

    /**
     * Returns the direction of the projectile used for multiplying horizontal velocity
     * @return the direction
     */
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

    /**
     * Applies upwards velocity to the projectile
     */
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
