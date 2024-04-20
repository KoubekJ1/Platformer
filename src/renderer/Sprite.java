package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Sprite {
    private HashMap<String, Animation> animations;
    private Animation currentAnimation;
    private int width;
    private int height;
    private boolean mirrored;

    public Sprite(String image, int width, int height) {
        this.width = width;
        this.height = height;
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

    public void setMirrored(boolean mirrored) {
        this.mirrored = mirrored;
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        if (currentAnimation == null) return null;

        BufferedImage image = currentAnimation.getCurrentImage();
        if (!mirrored) {
            return getProperlySizedImage(image, currentBlockSize);
        } else {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.concatenate(AffineTransform.getScaleInstance(-1, 1));
            affineTransform.concatenate(AffineTransform.getTranslateInstance(image.getWidth(), 0));

            BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D mirroredImageG2D = (Graphics2D) mirroredImage.getGraphics();
            mirroredImageG2D.transform(affineTransform);
            mirroredImageG2D.drawImage(image, 0, 0, null);
            mirroredImageG2D.dispose();

            return getProperlySizedImage(mirroredImage, currentBlockSize);
        }
    }

    private BufferedImage getProperlySizedImage(BufferedImage image, float currentBlockSize) {
        BufferedImage finalImage = (BufferedImage) image.getScaledInstance((int) (width * currentBlockSize), (int) (height * currentBlockSize), BufferedImage.SCALE_FAST);
        return finalImage;
    }
}
