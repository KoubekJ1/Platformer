package game.level;

import renderer.Sprite;

import java.awt.image.BufferedImage;

public class Block {
    private Sprite sprite;
    private boolean collision;

    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }
}
