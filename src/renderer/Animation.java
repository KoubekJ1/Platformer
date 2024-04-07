package renderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private ArrayList<BufferedImage> images;
    private int[] imageOrder;
    private boolean loop;
    private int currentImage;
    private Timer animationTimer;

    public Animation(BufferedImage image) {
        images = new ArrayList<>();
        images.add(image);
        loop = false;
        currentImage = 0;
    }

    public Animation(ArrayList<BufferedImage> images, int[] imageOrder, int delay, boolean loop) {
        initialize(images, imageOrder, delay, loop);
    }

    public Animation(ArrayList<BufferedImage> images, int delay, boolean loop) {
        int[] imageOrder = new int[images.size()];
        for (int i = 0; i < imageOrder.length; i++) {
            imageOrder[i] = i;
        }
        initialize(images, imageOrder, delay, loop);
    }

    public void initialize(ArrayList<BufferedImage> images, int[] imageOrder, int delay, boolean loop) {
        this.images = images;
        this.imageOrder = imageOrder;
        animationTimer = new Timer(delay, this);
        this.loop = loop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
