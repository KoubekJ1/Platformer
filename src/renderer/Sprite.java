package renderer;

import util.BlipTimer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.Timer;

public class Sprite implements Serializable {
    private HashMap<String, Animation> animations;
    private Animation currentAnimation;
    private float width;
    private float height;
    private boolean mirrored;
    private boolean blip;

    private float lastBlockSize = 0;
    private HashMap<BufferedImage, BufferedImage> mirroredImages = new HashMap<>();
    private HashMap<BufferedImage, BufferedImage> scaledImages = new HashMap<>();

    public Sprite(String image, float width, float height) {
        this.width = width;
        this.height = height;
        animations = new HashMap<>();
        animations.put("static", new Animation(image));
        animations.get("static").play();
        this.blip = false;
    }

    public Sprite(HashMap<String, Animation> animations, float width, float height) {
        this.animations = animations;
        this.width = width;
        this.height = height;
        this.blip = false;
    }

    public void playAnimation(String animation) {
        currentAnimation = animations.get(animation);
        currentAnimation.play();
    }

    public void stopAnimation() {
        if (currentAnimation == null) return;
        currentAnimation.stop();
        currentAnimation = null;
    }

    public void setMirrored(boolean mirrored) {
        this.mirrored = mirrored;
    }

    public boolean isMirrored() {
        return mirrored;
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        if (blip) {
            if (!BlipTimer.isBlip()) return null;
        }
        if (currentAnimation == null) {
            playAnimation("static");
        }

        BufferedImage image = currentAnimation.getCurrentImage();
        if (!mirrored) {
            return getProperlySizedImage(image, currentBlockSize);
        } else {
            // region "borrowed" code - Source: https://stackoverflow.com/questions/23457754/how-to-flip-bufferedimage-in-java
            if (mirroredImages.containsKey(image)) {
                return getProperlySizedImage(mirroredImages.get(image), currentBlockSize);
            }

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.concatenate(AffineTransform.getScaleInstance(-1, 1));
            affineTransform.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));

            BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D mirroredImageG2D = (Graphics2D) mirroredImage.getGraphics();
            mirroredImageG2D.transform(affineTransform);
            mirroredImageG2D.drawImage(image, 0, 0, null);
            mirroredImageG2D.dispose();
            // endregion

            mirroredImages.put(image, mirroredImage);
            return getProperlySizedImage(mirroredImage, currentBlockSize);
        }
    }

    private BufferedImage getProperlySizedImage(BufferedImage image, float currentBlockSize) {
        // Optimization
        if (currentBlockSize != lastBlockSize) {
            scaledImages.clear();
            lastBlockSize = currentBlockSize;
        }

        if (scaledImages.containsKey(image)) {
            return scaledImages.get(image);
        }

        lastBlockSize = currentBlockSize;
        Image scaledImage = image.getScaledInstance((int) (width * currentBlockSize), (int) (height * currentBlockSize), BufferedImage.SCALE_DEFAULT);
        BufferedImage finalImage = new BufferedImage((int) (width * currentBlockSize), (int) (height * currentBlockSize), BufferedImage.TYPE_INT_ARGB);
        finalImage.createGraphics().drawImage(scaledImage, 0, 0, null);
        scaledImages.put(image, finalImage);
        return finalImage;
    }

    public void blip() {
        blip = true;
    }

    public void stopBlipping() {
        blip = false;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAnimationTimeLeft() {
        return currentAnimation.getTimeLeft();
    }
}
