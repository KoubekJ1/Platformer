package game.level;

import renderer.Animation;
import renderer.Sprite;

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
    private static final int LARGE_PLAYER_HEIGHT = 1;
    private static final String PLAYER_TEXTURES_DIRECTORY = "assets/textures/player/";

    private Rectangle position;
    private Sprite sprite;

    public Player() {
        position = new Rectangle(0, 0, 1, 1);
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<BufferedImage> images = new ArrayList<>();

        try {
            images.add(ImageIO.read(new File(PLAYER_TEXTURES_DIRECTORY + "running/run1.png")));
            images.add(ImageIO.read(new File(PLAYER_TEXTURES_DIRECTORY + "running/run2.png")));
            images.add(ImageIO.read(new File(PLAYER_TEXTURES_DIRECTORY + "running/run3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        animations.put("running", new Animation(images, (1/60) * 1000, true));

        try {
            animations.put("static", new Animation(ImageIO.read(new File(PLAYER_TEXTURES_DIRECTORY + "static.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sprite = new Sprite(animations, PLAYER_WIDTH, SMALL_PLAYER_HEIGHT);
    }
}