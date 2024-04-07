package renderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
    private BufferedImage activeImage;
    private ArrayList<BufferedImage> images;
    private Timer animationTimer;
    private boolean loop;
}
