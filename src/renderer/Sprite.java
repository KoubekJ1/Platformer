package renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite implements ActionListener {
    private ArrayList<BufferedImage> images;
    private int activeImage;
    private Timer animationTimer;
    private boolean loop;
    private int width;
    private int height;

    public Sprite(BufferedImage image, int width, int height) {
        image = (BufferedImage) image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        images = new ArrayList<>();
        images.add(image);
        activeImage = 0;
    }

    public Sprite(ArrayList<BufferedImage> images, int period, boolean loop, int width, int height) {
        this.images = images;
        activeImage = 0;
        animationTimer = new Timer(period, this);
        this.loop = loop;
        this.width = width;
        this.height = height;
    }

    public void playAnimation() {
        animationTimer.start();
    }

    public void stopAnimation(boolean resetSprite) {
        animationTimer.stop();
        if (resetSprite) {
            activeImage = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == animationTimer) {
            if (activeImage >= images.size()) {
                activeImage = 0;
                if (!loop) {
                    stopAnimation(false);
                }
            }
        }
    }
}
