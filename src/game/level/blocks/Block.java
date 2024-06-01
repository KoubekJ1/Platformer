package game.level.blocks;

import game.level.blocks.collision.Break;
import game.level.blocks.collision.Collidable;
import renderer.Sprite;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Blocks serve as the foundation of a level. Together they create the area that the player moves around.
 */
public class Block implements Serializable, Cloneable {
    private static final String BLOCKS_DIRECTORY = "assets/blocks/";

    private String blockID;
    private String name;

    private Sprite sprite;
    private boolean collision;
    private Collidable collidable;

    /**
     * Creates a new Block with the given texture
     * @param texture the texture
     * @param collision whether the block has collision
     */
    public Block(String texture, boolean collision) {
        this.sprite = new Sprite(texture, 1, 1);
        this.collision = collision;
    }

    /**
     * Creates a new Block that performs a special action when hit
     * @param texture the texture
     * @param collision whether the block has collision
     * @param collidable the action that is performed when hit
     */
    public Block(String texture, boolean collision, Collidable collidable) {
        this.sprite = new Sprite(texture, 1, 1);
        this.collision = collision;
        this.collidable = collidable;
    }

    /**
     * Creates a new Block with a custom sprite that performs a special action when hit
     * @param sprite the sprite
     * @param collision whether the block has collision
     * @param collidable the action that is performed when hit
     */
    public Block(Sprite sprite, boolean collision, Collidable collidable) {
        this.sprite = sprite;
        this.collision = collision;
        this.collidable = collidable;
    }

    /**
     * Performs the action that happens when the block is hit
     * @param blockX the x-coordinate of the block
     * @param blockY the y-coordinate of the block
     */
    public void hit(int blockX, int blockY) {
        if (collidable == null) return;
        collidable.hit(blockX, blockY);
    }

    public boolean isCollision() {
        return collision;
    }

    /**
     * Returns the block's current texture
     * @param currentBlockSize the size of 1 game unit in pixels
     * @return the texture
     */
    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }

    /**
     * Serializes the block
     * Not used
     * @throws IOException in case there's an error
     */
    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(BLOCKS_DIRECTORY + blockID + ".block");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * Returns a brick block
     * @return brick
     */
    public static Block getBrick() {
        return new Block("blocks/brick.png", true, new Break());
    }

    /**
     * Returns an unbreakable brick block
     * @return castle brick
     */
    public static Block getCastleBrick() {
        return new Block("blocks/castle_brick.png", true);
    }

    /**
     * Returns an unbreakable block
     * @return unbreakable block
     */
    public static Block getUnbreakableBlock() {
        return new Block("blocks/unbreakable.png", true);
    }

    /**
     * Returns a ground block
     * @return ground block
     */
    public static Block getGround() {
        return new Block("blocks/ground.png", true);
    }

    /**
     * Returns pipe blocks
     * 0 - Upper left block
     * 1 - Upper right block
     * 2 - Lower left block
     * 3 - Lower right block
     * @return pipe blocks
     */
    public static Block[] getPipe() {
        return new Block[]{
            new Block("blocks/pipe/ul.png", true),
                new Block("blocks/pipe/ur.png", true),
                new Block("blocks/pipe/ll.png", true),
                new Block("blocks/pipe/lr.png", true)
        };
    }
}
