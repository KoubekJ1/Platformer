package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Sprite {
    private HashMap<String, Animation> animations;
    private Animation currentAnimation;
    private int width;
    private int height;

    public Sprite(BufferedImage image, int width, int height) {
        this.width = width;
        this.height = height;
        image = (BufferedImage) image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        animations = new HashMap<>();
        animations.put("static", new Animation(image));
        animations.get("static").play();
    }

    public Sprite(HashMap<String, Animation> animations, int width, int height) {
        this.animations = animations;
        this.width = width;
        this.height = height;
    }

    public void playAnimation(String animation) {
        currentAnimation = animations.get(animation);
        currentAnimation.play();
    }

    public void stopAnimation() {
        currentAnimation.stop();
        currentAnimation = null;
    }
}
