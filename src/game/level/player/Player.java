package game.level.player;

import game.ProgramManager;
import game.level.Block;
import game.level.player.camera.Camera;
import renderer.Animation;
import renderer.Sprite;
import util.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private int playerWidth;
    private int playerHeight;

    private static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    private static final float TERMINAL_VELOCITY = 50;
    private static final float JUMP_VELOCITY = 0.4f;
    private static final float GRAVITY_ACCELERATION = 1;
    private static final float MAX_RUNNING_SPEED = 0.15f;
    private static final float MAX_SPRINTING_SPEED = 0.3f;
    private static final float ACCELERATION = 1;
    //private static final float SPRINTING_ACCELERATION = 3;
    //private static final int DRAG_SLOWDOWN = 5;

    private float posX;
    private float posY;
    private float xVelocity;
    private float yVelocity;


    private Sprite sprite;
    private Camera camera;

    public Player() {
        posX = 0;
        posY = 0;
        xVelocity = 0;
        yVelocity = 0;

        playerWidth = 1;
        playerHeight = 1;

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

        sprite = new Sprite(animations, playerWidth, playerHeight);
    }

    public void update(float dt) {
        applyGravity(dt);
        // Checking collision with the ground must be done BEFORE jumping
        groundCollisionCheck();
        if (InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP)) jump();
        // Checking collision with the ceiling must be done AFTER jumping
        ceilingCollisionCheck();

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
            if (xVelocity < 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation(animation);
            }
            xVelocity += acceleration * dt;
            moving = true;
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_LEFT) || InputManager.isKeyPressed(KeyEvent.VK_A)) {
            sprite.setMirrored(true);
            if (xVelocity > 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation(animation);
            }
            xVelocity -= acceleration * dt;
            moving = !moving;
        }
        //endregion

        //region Drag force (+idle animation)
        if (!moving && xVelocity != 0) {
            applyDrag(dt, acceleration);
        } else if (xVelocity == 0) {
            sprite.stopAnimation();
        }
        //endregion

        //region Max speed check
        if (xVelocity > maxSpeed) {
            xVelocity = maxSpeed;
        } else if (xVelocity < -maxSpeed) {
            xVelocity = -maxSpeed;
        }
        //endregion

        //region Air check (for animation)
        if (yVelocity != 0) sprite.playAnimation("air");
        //endregion

        //region Out of bounds check
        if (posX + xVelocity < 0 || Math.ceil(posX) + xVelocity > ProgramManager.getLevel().getLevelSizeX() - 1) {
            xVelocity = 0;
        }
        if (posY + yVelocity < 0 || Math.ceil(posY) + playerHeight + yVelocity > ProgramManager.getLevel().getLevelSizeY() - 1) {
            yVelocity = 0;
        }
        //endregion

        rightBlockCollisionCheck();

        //region Apply velocity
        posX += xVelocity;
        posY += yVelocity;
        //endregion

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
        if (yVelocity < 0 && !(InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP))) {
            gravityEffectiveness = 3f;
        }
        yVelocity += GRAVITY_ACCELERATION * dt * gravityEffectiveness;
        if (yVelocity > TERMINAL_VELOCITY) {
            yVelocity = TERMINAL_VELOCITY;
        }
    }

    private void groundCollisionCheck() {
        for (float i = 0; i < yVelocity + 1 && yVelocity > 0; i++) {
            if (i >= yVelocity) {
                i = yVelocity;
            }
            if (Math.ceil(posY + i) >= ProgramManager.getLevel().getLevelSizeY()) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.ceil(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.ceil(posY + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                yVelocity = 0;
                posY = (float) Math.floor(posY + i);
                break;
            }
        }
    }

    private void ceilingCollisionCheck() {
        for (float i = 0; i > yVelocity - 1 && yVelocity < 0; i--) {
            if (i <= yVelocity) {
                i = yVelocity;
            }
            if (Math.floor(posY + i) <= 0) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.floor(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.floor(posY + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                yVelocity = 0;
                posY = (float) Math.ceil(posY + i);
                break;
            }
        }
    }

    private void rightBlockCollisionCheck() {
        for (float i = 0; i < xVelocity + 1 && xVelocity > 0; i++) {
            if (i >= xVelocity) {
                i = xVelocity;
            }
            if (Math.ceil(posX + i) >= ProgramManager.getLevel().getLevelSizeX()) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) (Math.ceil(posX + i)), (int) posY);
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX + i), (int) Math.ceil(posY));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                xVelocity = 0;
                posX = (float) Math.floor(posX + i);
                break;
            }
        }
    }

    private void jump() {
        if (yVelocity == 0) {
            yVelocity -= JUMP_VELOCITY;
        }

    }

    private void applyDrag(float dt, float acceleration) {
        if (xVelocity == 0) return;
        int direction = (int) (xVelocity / Math.abs(xVelocity));
        xVelocity -= acceleration * direction * dt;
        if (xVelocity / Math.abs(xVelocity) != direction) {
            xVelocity = 0;
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
        return new float[]{xVelocity, yVelocity};
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }
}