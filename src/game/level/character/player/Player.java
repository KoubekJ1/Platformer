package game.level.character.player;

import game.ProgramManager;
import game.level.Block;
import game.level.character.Character;
import game.level.character.player.camera.Camera;
import renderer.Animation;
import renderer.Sprite;
import util.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Character {

    private static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    private static final float TERMINAL_VELOCITY = 50;
    private static final float JUMP_VELOCITY = 0.4f;
    private static final float GRAVITY_ACCELERATION = 1;
    private static final float MAX_RUNNING_SPEED = 0.15f;
    private static final float MAX_SPRINTING_SPEED = 0.25f;
    private static final float ACCELERATION = 1;
    //private static final float SPRINTING_ACCELERATION = 3;
    //private static final int DRAG_SLOWDOWN = 5;

    private Sprite sprite;
    private Camera camera;

    public Player() {
        posX = 0;
        posY = 0;
        velocityX = 0;
        velocityY = 0;

        camera = new Camera(this);
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/static.png"));
        animations.put("air", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/air.png"));
        animations.put("turn", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/turn.png"));

        sprite = new Sprite(animations, 1, 1);
    }

    @Override
    public void characterUpdate(float dt) {
        if (InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP)) {
            jump();
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

        //region Out of bounds check
        if (posX + velocityX < 0 || Math.ceil(posX) + velocityX > ProgramManager.getLevel().getLevelSizeX() - 1) {
            velocityX = 0;
        }
        if (posY + velocityY < 0 || Math.ceil(posY) + getSize()[1] + velocityY > ProgramManager.getLevel().getLevelSizeY() - 1) {
            velocityY = 0;
        }
        //endregion

        if (rightBlockCollisionCheck()) {
            velocityX = 0;
        }
        if (leftBlockCollisionCheck()) {
            velocityX = 0;
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
        if (velocityY == 0) {
            velocityY -= JUMP_VELOCITY;
        }

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

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public float[] getVelocity() {
        return new float[]{velocityX, velocityY};
    }

    public float[] getSize() {
        return new float[]{sprite.getWidth(), sprite.getHeight()};
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }
}