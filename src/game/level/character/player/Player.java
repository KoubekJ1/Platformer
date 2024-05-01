package game.level.character.player;

import game.ProgramManager;
import game.level.character.DynamicObject;
import game.level.character.enemy.Enemy;
import game.level.character.player.camera.Camera;
import game.level.character.player.powerups.Powerup;
import game.level.character.player.powerups.states.Mushroom;
import game.level.character.player.powerups.states.PowerupState;
import renderer.Sprite;
import util.InputManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Player extends DynamicObject implements Serializable {

    private static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    private static final float TERMINAL_VELOCITY = 50;
    private static final float JUMP_VELOCITY = 0.4f;
    private static final float GRAVITY_ACCELERATION = 1;
    private static final float MAX_RUNNING_SPEED = 0.15f;
    private static final float MAX_SPRINTING_SPEED = 0.25f;
    private static final float ACCELERATION = 1;
    private static final int INVULNERABILITY_DURATION = 2000;
    //private static final float SPRINTING_ACCELERATION = 3;
    //private static final int DRAG_SLOWDOWN = 5;

    private Camera camera;
    private PowerupState powerupState;
    private Timer invulnerabilityTimer;

    public Player() {
        posX = 0;
        posY = 0;
        velocityX = 0;
        velocityY = 0;

        camera = new Camera(this);
        powerupState = new Mushroom(this);
        invulnerabilityTimer = new Timer(INVULNERABILITY_DURATION, e -> {
            invulnerabilityTimer.stop();
            sprite.stopBlipping();
        });
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
        for (Enemy enemy : ProgramManager.getLevel().getEnemies()) {
            if (!this.collision(enemy)) continue;
            if (this.velocityY > 0) {
                enemy.damage();
                jump();
            } else {
                if (!invulnerabilityTimer.isRunning()) this.damage();
            }
        }

        for (Powerup powerup : ProgramManager.getLevel().getPowerups()) {
            if (!this.collision(powerup)) continue;
            this.setPowerupState(powerup.collectPowerup(this));
            enableInvulnerability();
        }

        updateCamera();

        //region Debug
        if (!ProgramManager.isDebug()) return;
        if (InputManager.isKeyPressed(KeyEvent.VK_PAGE_UP)) {
            camera.setWorldScale(camera.getTransform().getScaleX() + 1 * dt);
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_PAGE_DOWN)) {
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
        enableInvulnerability();
    }

    @Override
    public void kill() {
        sprite.playAnimation("kill");
        JOptionPane.showMessageDialog(null, "You are dead!", "Game Over", JOptionPane.PLAIN_MESSAGE);
        ProgramManager.endLevel();
        Thread.currentThread().stop();
    }

    private void enableInvulnerability() {
        invulnerabilityTimer.start();
        sprite.blip();
    }

    private void applyGravity(float dt) {
        float gravityEffectiveness = 1;
        if (velocityY < 0 && !(InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP))) {
            gravityEffectiveness = 3f;
        }
        velocityY += GRAVITY_ACCELERATION * dt * gravityEffectiveness;
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
    }

    private void jump() {
        velocityY = -JUMP_VELOCITY;
    }

    private void applyDrag(float dt, float acceleration) {
        if (velocityX == 0) return;
        int direction = (int) (velocityX / Math.abs(velocityX));
        velocityX -= acceleration * direction * dt;
        if (velocityX / Math.abs(velocityX) != direction) {
            velocityX = 0;
        }
    }

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

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
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