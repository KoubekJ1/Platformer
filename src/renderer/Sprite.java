package renderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
    private ArrayList<BufferedImage> images;
    private int activeImage;
    private Timer animationTimer;
    private boolean loop;
    private int width;
    private int height;

    public Sprite(BufferedImage image, int width, int height) {
        images = new ArrayList<>();
        images.add(image);
        activeImage = 0;
    }


}
