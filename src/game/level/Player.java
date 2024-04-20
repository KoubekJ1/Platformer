package game.level;

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

    private float posX;
    private float posY;

    private Sprite sprite;
    private Camera camera;

    public Player() {
        posX = 0;
        posY = 0;
        camera = new Camera();
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "run3.png");

        animations.put("running", new Animation(images, (1 / 60) * 1000, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "static.png"));

        sprite = new Sprite(animations, PLAYER_WIDTH, SMALL_PLAYER_HEIGHT);
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