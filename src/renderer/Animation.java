package renderer;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Timer;

public class Animation {
    private HashSet<BufferedImage> images;
    private int[] imageOrder;
    private boolean loop;
    private int currentImage;
    private Timer animationTimer;
}
