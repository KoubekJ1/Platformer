package game.level;

import renderer.Sprite;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Block implements Serializable, Cloneable {
    private static final String BLOCKS_DIRECTORY = "assets/blocks/";

    private String blockID;
    private String name;

    private Sprite sprite;
    private boolean collision;

    public Block(Sprite sprite, boolean collision) {
        this.sprite = sprite;
        this.collision = collision;
    }

    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(BLOCKS_DIRECTORY + blockID + ".block");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
