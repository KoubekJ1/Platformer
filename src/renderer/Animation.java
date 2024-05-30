package renderer;

import util.AssetManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An Animation instance represents a list of textures displayed in a given order with a certain interval between texture switching
 * This can be used to create a motion effect (for example walking)
 */
public class Animation implements ActionListener, Serializable {
    private ArrayList<String> images;
    private int[] imageOrder;
    private boolean loop;
    private int currentImage;
    private Timer animationTimer;

    public Animation() {
    }

    /**
     * Creates an animation consisting of just 1 image
     * @param image the image to be shown
     */
    public Animation(String image) {
        images = new ArrayList<>();
        images.add(image);
        imageOrder = new int[1];
        loop = false;
        currentImage = 0;
    }

    /**
     * Creates an animation consisting of the given images displayed in the given order (0 - first image, 1 - second image etc.) with the given delay between texture switching
     * @param images the images to be displayed
     * @param imageOrder the order of the images
     * @param delay the delay between image switching
     * @param loop whether the animation is supposed to loop
     */
    public Animation(ArrayList<String> images, int[] imageOrder, int delay, boolean loop) {
        initialize(images, imageOrder, delay, loop);
    }

    /**
     * Creates an animation consisting of the given images displayed in their given order with the given delay between texture switching
     * @param images the images to be displayed
     * @param delay the delay between image switching
     * @param loop whether the animation is supposed to loop
     */
    public Animation(ArrayList<String> images, int delay, boolean loop) {
        int[] imageOrder = new int[images.size()];
        for (int i = 0; i < imageOrder.length; i++) {
            imageOrder[i] = i;
        }
        initialize(images, imageOrder, delay, loop);
    }

    /**
     * Initializes the animation with the given arguments
     * @param images the images to be displayed
     * @param imageOrder the order of the images
     * @param delay the delay between image switching
     * @param loop whether the animation is supposed to loop
     */
    public void initialize(ArrayList<String> images, int[] imageOrder, int delay, boolean loop) {
        this.images = images;
        this.imageOrder = imageOrder;
        this.animationTimer = new Timer(delay, this);
        animationTimer.setInitialDelay(0);
        this.loop = loop;
    }

    /**
     * Plays the animation (enables the texture switching)
     */
    public void play() {
        if (images.size() <= 1) {
            return;
        }
        animationTimer.start();
    }

    /**
     * Stops the animation
     */
    public void stop() {
        if (images.size() <= 1) {
            return;
        }
        animationTimer.stop();
    }

    /**
     * Factory method for an animation shown upon a character's death
     * @param texturePath the texture to be shown
     * @return the kill animation
     */
    public static Animation getKillAnimation(String texturePath) {
        ArrayList<String> killTextures = new ArrayList<>();
        killTextures.add(texturePath);
        Animation killAnimation = new Animation(killTextures, new int[8], 100, false);

        return killAnimation;
    }

    /**
     * Returns how much time there's left before the animation's over
     * @return how much time there's left before the animation's over
     */
    public int getTimeLeft() {
        return animationTimer.getDelay() * (imageOrder.length - currentImage - 1);
    }

    /**
     * Gets the currently shown texture
     * @return the currently shown texture
     */
    public BufferedImage getCurrentImage() {
        return AssetManager.getTexture(images.get(imageOrder[currentImage]));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == animationTimer) {
            currentImage++;
            if (currentImage >= imageOrder.length) {
                if (loop) {
                    currentImage = 0;
                }
            }
        }
    }
}
