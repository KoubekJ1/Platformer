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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
