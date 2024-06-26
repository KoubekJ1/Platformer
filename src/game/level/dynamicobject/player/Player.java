package game.level.dynamicobject.player;

import game.ProgramManager;
import game.level.Score;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.Finish;
import game.level.dynamicobject.enemy.Enemy;
import game.level.dynamicobject.enemy.Piranha;
import game.level.dynamicobject.player.camera.Camera;
import game.level.dynamicobject.player.powerups.Powerup;
import game.level.dynamicobject.player.powerups.states.Fire;
import game.level.dynamicobject.player.powerups.states.Mushroom;
import game.level.dynamicobject.player.powerups.states.PowerupState;
import game.level.dynamicobject.player.powerups.states.Small;
import util.InputManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 * A Player instance represents a playable character in the level
 */
public class Player extends DynamicObject {
    private static final float JUMP_VELOCITY = 0.4f;
    private static final float GRAVITY_ACCELERATION = 1;
    private static final float MAX_RUNNING_SPEED = 0.15f;
    private static final float MAX_SPRINTING_SPEED = 0.25f;
    private static final float ACCELERATION = 1;
    private static final int INVULNERABILITY_DURATION = 2000;

    private Camera camera;
    private PowerupState powerupState;
    private Timer invulnerabilityTimer;

    /**
     * Creates a new Player with a FollowPlayer camera strategy and the basic PowerupState in the given coordinates
     * @param x x position
     * @param y y position
     */
    public Player(int x, int y) {
        this.posX = x;
        this.posY = y;
        velocityX = 0;
        velocityY = 0;

        camera = new Camera(this);
        invulnerabilityTimer = new Timer(INVULNERABILITY_DURATION, e -> {
            invulnerabilityTimer.stop();
            sprite.stopBlipping();
        });
        powerupState = new Small(this);
    }

    @Override
    public void objectUpdate(float dt) {
        if (InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP)) {
            if (velocityY == 0) jump();
        } else {
            if (velocityY < 0) {
                velocityY += 2 * GRAVITY_ACCELERATION * dt;
            }
        }
        // Checking collision with the ceiling must be done AFTER jumping
        if (ceilingCollisionCheck()) {
            velocityY = 0;
        }

        // region Horizontal movement
        float acceleration;
        float maxSpeed;
        String animation;

        if (InputManager.isKeyPressed(KeyEvent.VK_SHIFT)) {
            acceleration = ACCELERATION;
            maxSpeed = MAX_SPRINTING_SPEED;
            animation = "sprinting";
        } else {
            acceleration = ACCELERATION;
            maxSpeed = MAX_RUNNING_SPEED;
            animation = "running";
        }

        boolean moving = false;
        if (InputManager.isKeyPressed(KeyEvent.VK_RIGHT) || InputManager.isKeyPressed(KeyEvent.VK_D)) {
            sprite.setMirrored(false);
            if (velocityX < 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation(animation);
            }
            velocityX += acceleration * dt;
            moving = true;
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_LEFT) || InputManager.isKeyPressed(KeyEvent.VK_A)) {
            sprite.setMirrored(true);
            if (velocityX > 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation(animation);
            }
            velocityX -= acceleration * dt;
            moving = !moving;
        }
        //endregion

        //region Drag force (+idle animation)
        if (!moving && velocityX != 0) {
            applyDrag(dt, acceleration);
        } else if (velocityX == 0) {
            sprite.stopAnimation();
        }
        //endregion

        //region Max speed check
        if (velocityX > maxSpeed) {
            velocityX = maxSpeed;
        } else if (velocityX < -maxSpeed) {
            velocityX = -maxSpeed;
        }
        //endregion

        //region Air check (for animation)
        if (velocityY != 0) sprite.playAnimation("air");
        //endregion

        if (rightBlockCollisionCheck()) {
            velocityX = 0;
        }
        if (leftBlockCollisionCheck()) {
            velocityX = 0;
        }

        if (InputManager.isKeyPressed(KeyEvent.VK_E) || InputManager.isKeyPressed(KeyEvent.VK_ENTER)) {
            powerupState.ability();
        }

        for (Enemy enemy : ProgramManager.getLevel().getEnemies()) {
            if (!this.collision(enemy)) continue;
            if (this.velocityY > 0 && enemy.getAi().getClass() != Piranha.class) {
                enemy.damage();
                jump();
                ProgramManager.getLevel().getScore().addScore(Score.ENEMY_DAMAGE_SCORE * ProgramManager.getLevel().getScore().getMultiplier(this));
                ProgramManager.getLevel().getScore().increaseMultiplier(this);
                break;
            } else {
                if (!invulnerabilityTimer.isRunning()) this.damage();
            }
        }

        for (Powerup powerup : ProgramManager.getLevel().getPowerups()) {
            if (!this.collision(powerup)) continue;
            this.setPowerupState(powerup.collectPowerup(this));
        }

        updateCamera();

        for (Finish finish : ProgramManager.getLevel().getFinishPoints()) {
            if (collision(finish)) {
                ProgramManager.getLevel().finish();
            }
        }

        //region Debug
        if (!ProgramManager.isDebug()) return;
        if (InputManager.isKeyPressed(KeyEvent.VK_PAGE_UP) || InputManager.isKeyPressed(KeyEvent.VK_N)) {
            camera.setWorldScale(camera.getTransform().getScaleX() + 1 * dt);
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_PAGE_DOWN)|| InputManager.isKeyPressed(KeyEvent.VK_M)) {
            camera.setWorldScale(camera.getTransform().getScaleX() - 1 * dt);
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_I)) {
            camera.setPosition(camera.getTransform().getTranslateX(), camera.getTransform().getTranslateY() + 10 * dt);
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_K)) {
            camera.setPosition(camera.getTransform().getTranslateX(), camera.getTransform().getTranslateY() - 10 * dt);
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_J)) {
            camera.setPosition(camera.getTransform().getTranslateX() + 10 * dt, camera.getTransform().getTranslateY());
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_L)) {
            camera.setPosition(camera.getTransform().getTranslateX() - 10 * dt, camera.getTransform().getTranslateY());
        }
        //endregion
    }

    @Override
    protected void damage() {
        powerupState.damage();
    }

    @Override
    public void kill() {
        sprite.playAnimation("kill");
        JOptionPane.showMessageDialog(null, "You are dead!", "Game Over", JOptionPane.PLAIN_MESSAGE);
        ProgramManager.endLevel();
        Thread.currentThread().stop();
    }

    /**
     * Sets the player as invulnerable for a short while
     * Also makes the player blip for as long as he's invulnerable
     */
    public void enableInvulnerability() {
        invulnerabilityTimer.start();
        sprite.blip();
    }

    @Override
    public String getObjectCategory() {
        return "player";
    }

    /**
     * Gives the player upwards velocity representing a jump
     */
    private void jump() {
        velocityY = -JUMP_VELOCITY;
    }

    /**
     * Slows down the player representing drag force
     * @param dt time between update() calls
     * @param acceleration player acceleration speed
     */
    private void applyDrag(float dt, float acceleration) {
        if (velocityX == 0) return;
        int direction = (int) (velocityX / Math.abs(velocityX));
        velocityX -= acceleration * direction * dt;
        if (velocityX / Math.abs(velocityX) != direction) {
            velocityX = 0;
        }
    }

    /**
     * Updates the camera
     */
    public void updateCamera() {
        camera.update();
    }

    public Camera getCamera() {
        return camera;
    }

    public float[] getVelocity() {
        return new float[]{velocityX, velocityY};
    }

    public float[] getSize() {
        return new float[]{sprite.getWidth(), sprite.getHeight()};
    }

    public void setPowerupState(PowerupState powerupState) {
        this.powerupState = powerupState;
    }

    public PowerupState getPowerupState() {
        return this.powerupState;
    }

    @Override
    protected boolean groundCollisionCheck() {
        boolean isCollision = super.groundCollisionCheck();
        if (isCollision) {
            ProgramManager.getLevel().getScore().resetMultiplier(this);
        }
        return isCollision;
    }

    @Override
    protected String getAssetDirectory() {
        return "players";
    }

    @Override
    protected String getAssetExtension() {
        return ".player";
    }
}