package renderer;

import util.AssetManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private ArrayList<String> images;
    private int[] imageOrder;
    private boolean loop;
    private int currentImage;
    private Timer animationTimer;

    public Animation(String image) {
        images = new ArrayList<>();
        images.add(image);
        imageOrder = new int[1];
        loop = false;
        currentImage = 0;
    }

    public Animation(ArrayList<String> images, int[] imageOrder, int delay, boolean loop) {
        initialize(images, imageOrder, delay, loop);
    }

    public Animation(ArrayList<String> images, int delay, boolean loop) {
        int[] imageOrder = new int[images.size()];
        for (int i = 0; i < imageOrder.length; i++) {
            imageOrder[i] = i;
        }
        initialize(images, imageOrder, delay, loop);
    }

    public void initialize(ArrayList<String> images, int[] imageOrder, int delay, boolean loop) {
        this.images = images;
        this.imageOrder = imageOrder;
        this.animationTimer = new Timer(delay, this);
        animationTimer.setInitialDelay(0);
        this.loop = loop;
    }

    public void play() {
        if (images.size() <= 1) {
            return;
        }
        animationTimer.start();
    }

    public void stop() {
        if (images.size() <= 1) {
            return;
        }
        animationTimer.stop();
    }

    public int getTimeLeft() {
        return animationTimer.getDelay() * (imageOrder.length - currentImage - 1);
    }

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
