package renderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
    private BufferedImage activeImage;
    private ArrayList<BufferedImage> images;
    private float switchFrequency;
    private boolean loop;
}
