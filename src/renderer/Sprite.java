package renderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Sprite {
    private HashMap<String, Animation> animations;
    private Animation currentAnimation;
    private float width;
    private float height;
    private boolean mirrored;

    private float lastBlockSize = 0;
    private HashMap<BufferedImage, BufferedImage> mirroredImages = new HashMap<>();
    private HashMap<BufferedImage, BufferedImage> scaledImages = new HashMap<>();

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
        if (currentAnimation == null) {
            playAnimation("static");
        }

        BufferedImage image = currentAnimation.getCurrentImage();
        if (!mirrored) {
            return getProperlySizedImage(image, currentBlockSize);
        } else {
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
