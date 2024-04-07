package renderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Timer;

public class Animation implements ActionListener {
    private HashSet<BufferedImage> images;
    private int[] imageOrder;
    private boolean loop;
    private int currentImage;
    private Timer animationTimer;


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
