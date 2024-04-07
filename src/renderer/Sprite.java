package renderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
    private ArrayList<BufferedImage> images;
    private int activeImage;
    private Timer animationTimer;
    private boolean loop;

    public Sprite(BufferedImage image) {
        images = new ArrayList<>();
        images.add(image);
        activeImage = 0;
    }


}
