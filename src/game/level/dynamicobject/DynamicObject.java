package game.level.dynamicobject;
import game.ProgramManager;
import game.level.blocks.Block;
import renderer.Sprite;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * DynamicObject instances represent game objects that perform some sort of activity, namely moving across the level.
 */
public abstract class DynamicObject implements Serializable {
    public static final int GRAVITY_ACCELERATION = 1;
    private static final float TERMINAL_VELOCITY = 50;
    public static final String CHARACTER_TEXTURES_PATH = "assets/textures/characters/";
    protected Sprite sprite;
    protected String id;
    protected String name;
    protected float posX;
    protected float posY;
    protected float velocityX;
    protected float velocityY;
    private boolean hasCollision;
    private boolean gravity;

    /**
     * Creates a new DynamicObject
     * By default the DynamicObject is affected by gravity and has collision enabled
     */
    public DynamicObject() {
        gravity = true;
        hasCollision = true;
    }

    /**
     * Updates the object
     * When called, the object will perform its given activity, including movement (gravity + other movement specified in subclasses)
     * This method should be called about 60 times per second
     * @param dt time between update() calls in seconds
     */
    public void update(float dt) {
        if (gravity) {
            applyGravity(dt);
            if (groundCollisionCheck()) {
                velocityY = 0;
            }
        }
        objectUpdate(dt);
        if (isOutOfBoundsX()) velocityX = 0;
        if (isOutOfBoundsY()) {
            velocityY = 0;
            kill();
        }
        posX += velocityX;
        posY += velocityY;
    }

    /**
     * Updates the object in ways specific to the subclass
     * @param dt time between update() calls in seconds
     */
    protected abstract void objectUpdate(float dt);

    /**
     * Damages the object
     */
    protected abstract void damage();

    /**
     * Kills the object
     * Usually results in the object being removed from the level
     */
    public abstract void kill();

    /**
     * Applies gravity acceleration to the object
     * @param dt time between update() calls in seconds
     */
    private void applyGravity(float dt) {
        velocityY += GRAVITY_ACCELERATION * dt;
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
    }

    /**
     * Checks whether the object is going to collide with the ground given its velocity and moves it accordingly
     * Note that this method does not stop its velocity
     * @return whether the object is going to collide with the ground given its velocity
     */
    protected boolean groundCollisionCheck() {
        for (float i = 0; i < velocityY + 1 && velocityY > 0; i++) {
            if (i >= velocityY) {
                i = velocityY;
            }
            if (Math.ceil(posY + i) >= ProgramManager.getLevel().getLevelSizeY()) break;
            // To avoid rounding errors - Just sprite.getWidth() results in getting stuck in the wall
            for (float j = 0; j <= sprite.getWidth() - 1 + (Math.ceil(posX) - (int) posX); j++) {
                Block block = ProgramManager.getLevel().getBlock((int) (posX + j), (int) (posY + sprite.getHeight() + i));
                if (block != null && block.isCollision()) {
                    posY = (float) Math.floor(posY + i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the object is going to collide with the ceiling given its velocity and moves it accordingly
     * Note that this method does not stop its velocity
     * @return whether the object is going to collide with the ceiling given its velocity
     */
    public boolean ceilingCollisionCheck() {
        for (float i = 0; i > velocityY - 1 && velocityY < 0; i--) {
            if (i <= velocityY) {
                i = velocityY;
            }
            if (Math.floor(posY + i) < 0) break;
            LinkedList<Block> blocks = new LinkedList<>();
            for (float j = 0; j <= sprite.getWidth() - 1 + (Math.ceil(posX) - (int) posX); j++) {
                int blockX = (int) (posX + j);
                int blockY = (int) (posY + i);
                Block block = ProgramManager.getLevel().getBlock(blockX, blockY);
                if (block != null && block.isCollision()) {
                    posY = (float) Math.ceil(posY + i);
                    block.hit(blockX, blockY);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the object is going to collide with blocks on its right given its velocity and moves it accordingly
     * Note that this method does not stop its velocity
     * @return whether the object is going to collide with blocks on its right given its velocity
     */
    public boolean rightBlockCollisionCheck() {
        for (float i = 0; i < velocityX + 1 && velocityX > 0; i++) {
            if (i >= velocityX) {
                i = velocityX;
            }
            if (Math.ceil(posX + i) >= ProgramManager.getLevel().getLevelSizeX()) break;
            for (float j = 0; j < sprite.getHeight(); j++) {
                Block block = ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth() + i), (int) (posY + j));
                if (block != null && block.isCollision()) {
                    posX = (float) Math.floor(posX + i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the object is going to collide with the ground given its velocity and moves it accordingly
     * Note that this method does not stop its velocity
     * @return whether the object is going to collide with the ground given its velocity
     */
    public boolean leftBlockCollisionCheck() {
        for (float i = 0; i > velocityX - 1 && velocityX < 0; i--) {
            if (i <= velocityX) {
                i = velocityX;
            }
            if (Math.floor(posX + i) < 0) break;
            for (float j = 0; j < sprite.getHeight(); j++) {
                Block block = ProgramManager.getLevel().getBlock((int) (posX + i), (int) (posY + j));
                if (block != null && block.isCollision()) {
                    posX = (float) Math.ceil(posX + i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether this object is colliding with the given dynamic object
     * @param dynamicObject the dynamic object to check for collision
     * @return whether they are colliding
     */
    public boolean collision(DynamicObject dynamicObject) {
        if (this == dynamicObject) return false;
        if (this.sprite == null || dynamicObject.sprite == null) {
            return false;
        }
        if (!this.hasCollision || !dynamicObject.hasCollision) return false;
        if (this.posX + this.getSizeX() <= dynamicObject.posX) return false;
        if (this.posX >= dynamicObject.posX + dynamicObject.getSizeX()) return false;
        if (this.posY + this.getSizeY() <= dynamicObject.posY) return false;
        if (this.posY >= dynamicObject.posY + dynamicObject.getSizeY()) return false;
        return true;
    }

    /**
     * Checks whether the object is out of bounds on the x-axis given its velocity
     * @return whether the object is out of bounds on the x-axis given its velocity
     */
    private boolean isOutOfBoundsX() {
        return posX + velocityX < 0 || Math.ceil(posX) + velocityX > ProgramManager.getLevel().getLevelSizeX() - 1;
    }

    /**
     * Checks whether the object is out of bounds on the y-axis given its velocity
     * @return whether the object is out of bounds on the y-axis given its velocity
     */
    private boolean isOutOfBoundsY() {
        return posY + velocityY < 0 || Math.ceil(posY) + getSizeY() + velocityY > ProgramManager.getLevel().getLevelSizeY() - 1;
    }

    /**
     * Gets the object's current texture
     * @param currentBlockSize the size of 1 game unit in pixels
     * @return the current texture
     */
    public BufferedImage getCurrentImage(float currentBlockSize) {
        return sprite.getCurrentImage(currentBlockSize);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getSizeX() {
        return sprite.getWidth();
    }

    public void setSizeX(float sizeX) {
        sprite.setWidth(sizeX);
    }

    public float getSizeY() {
        return sprite.getHeight();
    }

    /**
     * Sets the object's height
     * This method also makes sure that the object will not collide with ground by moving it up or down based on the size difference
     * @param sizeY the new height
     */
    public void setSizeY(float sizeY) {
        if (getSprite() != null) {
            float sizeDifference = getSizeY() - sizeY;
            setPosY(getPosY() + sizeDifference);
            sprite.setHeight(sizeY);
        }
    }

    public boolean hasCollision() {
        return hasCollision;
    }

    public void setCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    /**
     * Sets the object's sprite
     * If there's a height difference between the current and the new sprite, the object will be moved accordingly
     * @param sprite the new sprite
     */
    public void setSprite(Sprite sprite) {
        if (getSprite() != null) {
            float sizeDifference = getSizeY() - sprite.getHeight();
            setPosY(getPosY() + sizeDifference);
        }
        this.sprite = sprite;
    }

    /**
     * Serializes the object into its asset category folder
     * Currently unused
     * @throws IOException in case there's an error
     */
    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("assets/" + getAssetDirectory() + "/" + id + getAssetExtension());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * Gets what category this object belongs to
     * @return object category
     */
    public abstract String getObjectCategory();

    /**
     * Gets the file directory in the assets folder that this object belongs to in case of serialization
     * @return file directory
     */
    protected abstract String getAssetDirectory();

    /**
     * Gets the file extension of this object type in case of serialization
     * @return file extension
     */
    protected abstract String getAssetExtension();

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
