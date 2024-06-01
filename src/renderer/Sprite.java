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

/**
 * Sprite serves as a texture manager for the given object
 * Each sprite has its animations, which define which texture is supposed to be shown at each moment
 * Every sprite needs at least 1 animation to function properly - "static" (shown when the object is not doing anything)
 * DynamicObject subclasses also require a "kill" animation (shown when the object is dying and blipping away)
 */
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

    /**
     * Constructs a new Sprite and creates its static animation from the given texture
     * @param image the texture's path within the "textures/" folder
     * @param width the width of the object in game units (1 block)
     * @param height the height of the object in game units (1 block)
     */
    public Sprite(String image, float width, float height) {
        this.width = width;
        this.height = height;
        animations = new HashMap<>();
        animations.put("static", new Animation(image));
        animations.get("static").play();
        this.blip = false;
    }

    /**
     * Constructs a new Sprite with the given animations
     * The animation HashMap must contain the "static" animation
     * @param animations the sprite's animations
     * @param width the width of the object in game units (1 block)
     * @param height the height of the object in game units (1 block)
     */
    public Sprite(HashMap<String, Animation> animations, float width, float height) {
        this.animations = animations;
        this.width = width;
        this.height = height;
        this.blip = false;
    }

    /**
     * Plays the selected animation
     * @param animation the animation
     */
    public void playAnimation(String animation) {
        currentAnimation = animations.get(animation);
        currentAnimation.play();
    }

    /**
     * Stops playing the current animation
     */
    public void stopAnimation() {
        if (currentAnimation == null) return;
        currentAnimation.stop();
        currentAnimation = null;
    }

    /**
     * Sets whether the sprite should be displayed as mirrored
     * Used to prevent having to provide mirrored textures and animations
     * @param mirrored whether the sprite should be displayed as mirrored
     */
    public void setMirrored(boolean mirrored) {
        this.mirrored = mirrored;
    }

    /**
     * Returns whether the sprite is currently mirrored
     * @return whether the sprite is currently mirrored
     */
    public boolean isMirrored() {
        return mirrored;
    }

    /**
     * Gets the currently playing animation's current texture
     * @param currentBlockSize the size of 1 game unit in pixels
     * @return the texture
     */
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

    /**
     * Resizes the given image to its correct size given its size in game units and the game unit's pixel count
     * @param image the image to be resized
     * @param currentBlockSize the size of 1 game unit in pixels
     * @return the resized image
     */
    private BufferedImage getProperlySizedImage(BufferedImage image, float currentBlockSize) {
        if (image == null) return null;
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

    /**
     * Sets this sprite to blip
     */
    public void blip() {
        blip = true;
    }

    /**
     * Sets this sprite to stop blipping
     */
    public void stopBlipping() {
        blip = false;
    }

    /**
     * Gets the width of this sprite in game units
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of this sprite in game units
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the width of this sprite in game units
     * @param width the width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Sets the height of this sprite in game units
     * @param height the height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Returns how much time is left in the current animation
     * @return the time
     */
    public int getAnimationTimeLeft() {
        return currentAnimation.getTimeLeft();
    }
}
