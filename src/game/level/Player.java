package game.level;

import game.ProgramManager;
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

    private static final int TERMINAL_VELOCITY = 50;
    private static final float JUMP_VELOCITY = 0.5f;
    private static final int GRAVITY_ACCELERATION = 2;
    private static final float MAX_RUNNING_SPEED = 0.25f;
    private static final float MAX_SPRINTING_SPEED = 0.5f;
    private static final int RUNNING_ACCELERATION = 2;
    private static final int SPRINTING_ACCELERATION = 3;
    private static final int DRAG_SLOWDOWN = 5;

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

        camera = new Camera();
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/static.png"));
        animations.put("air", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/turn.png"));
        animations.put("turn", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/turn.png"));

        sprite = new Sprite(animations, playerWidth, playerHeight);
    }

    public void update(float dt) {
        //region Gravity
        yVelocity += GRAVITY_ACCELERATION * dt;
        if (yVelocity > TERMINAL_VELOCITY) {
            yVelocity = TERMINAL_VELOCITY;
        }
        //endregion

        // region Movement left and right
        boolean moving = false;
        if (InputManager.isKeyPressed(KeyEvent.VK_RIGHT) || InputManager.isKeyPressed(KeyEvent.VK_D)) {
            sprite.setMirrored(false);
            if (xVelocity < 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation("running");
            }
            xVelocity += RUNNING_ACCELERATION * dt;
            moving = true;
        }
        if (InputManager.isKeyPressed(KeyEvent.VK_LEFT) || InputManager.isKeyPressed(KeyEvent.VK_A)) {
            sprite.setMirrored(true);
            if (xVelocity > 0) {
                sprite.playAnimation("turn");
            } else {
                sprite.playAnimation("running");
            }
            xVelocity -= RUNNING_ACCELERATION * dt;
            moving = !moving;
        }
        //endregion

        //region Drag force
        if (!moving && xVelocity != 0) {
            applyDrag(dt);
        } else if (xVelocity == 0) {
            sprite.stopAnimation();
        }
        //endregion

        //region Max speed check
        if (xVelocity > MAX_RUNNING_SPEED) {
            xVelocity = MAX_RUNNING_SPEED;
        } else if (xVelocity < -MAX_RUNNING_SPEED) {
            xVelocity = -MAX_RUNNING_SPEED;
        }
        //endregion

        //region Ground collision check
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
        //endregion

        //region Jumping
        if (InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP)) {
            if (yVelocity == 0) {
                yVelocity -= JUMP_VELOCITY;
            }
        }
        //endregion

        //region Out of bounds check
        if (posX + xVelocity < 0 || Math.ceil(posX) + xVelocity >= ProgramManager.getLevel().getLevelSizeX()) {
            xVelocity = 0;
        }
        if (posY + yVelocity < 0 || Math.ceil(posY) + playerHeight + yVelocity >= ProgramManager.getLevel().getLevelSizeY()) {
            yVelocity = 0;
        }
        //endregion

        //region Apply velocity
        posX += xVelocity;
        posY += yVelocity;
        //endregion
    }

    private void applyDrag(float dt) {
        if (xVelocity == 0) return;
        int direction = (int) (xVelocity / Math.abs(xVelocity));
        xVelocity -= DRAG_SLOWDOWN * direction * dt;
        if (xVelocity / Math.abs(xVelocity) != direction) {
            xVelocity = 0;
        }
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