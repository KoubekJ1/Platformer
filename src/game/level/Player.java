package game.level;

import game.ProgramManager;
import renderer.Animation;
import renderer.Sprite;
import util.AssetManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private static final int PLAYER_WIDTH = 1;
    private static final int SMALL_PLAYER_HEIGHT = 1;
    private static final int LARGE_PLAYER_HEIGHT = 2;
    private static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";
    private static final int TERMINAL_VELOCITY = 50;
    private static final int JUMP_VELOCITY = 3;
    private static final int GRAVITY_ACCELERATION = 10;

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

        camera = new Camera();
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "running/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "running/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "running/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "static.png"));

        sprite = new Sprite(animations, PLAYER_WIDTH, SMALL_PLAYER_HEIGHT);
        sprite.playAnimation("running");
    }

    public void update(float dt) {
        // Apply gravity
        yVelocity += GRAVITY_ACCELERATION * dt;
        if (yVelocity > TERMINAL_VELOCITY) {
            yVelocity = TERMINAL_VELOCITY;
        }

        // Check if the player is touching the ground
        for (int i = 0; i <= yVelocity; i++) {
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.ceil(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.ceil(posY + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                yVelocity = 0;
                //posY = (float) Math.floor(posY);
                break;
            }
        }

        posY += yVelocity;
    }

    public Camera getCamera() {
        return camera;
    }

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }
}