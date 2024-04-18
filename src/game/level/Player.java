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
    private static final String PLAYER_TEXTURES_DIRECTORY = "player/";

    private Rectangle position;
    private Sprite sprite;
    private Camera camera;

    public Player() {
        position = new Rectangle(0, 0, PLAYER_WIDTH, SMALL_PLAYER_HEIGHT);
        camera = new Camera();
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<BufferedImage> images = new ArrayList<>();

        images.add(AssetManager.getTexture(PLAYER_TEXTURES_DIRECTORY + "run1.png"));
        images.add(AssetManager.getTexture(PLAYER_TEXTURES_DIRECTORY + "run2.png"));
        images.add(AssetManager.getTexture(PLAYER_TEXTURES_DIRECTORY + "run3.png"));

        animations.put("running", new Animation(images, (1 / 60) * 1000, true));
        animations.put("static", new Animation(AssetManager.getTexture(PLAYER_TEXTURES_DIRECTORY + "static.png")));

        sprite = new Sprite(animations, PLAYER_WIDTH, SMALL_PLAYER_HEIGHT);
    }

    public Camera getCamera() {
        return camera;
    }
}