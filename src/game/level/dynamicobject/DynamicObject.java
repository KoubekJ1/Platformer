package game.level.dynamicobject;

import game.ProgramManager;
import game.level.Block;
import renderer.Sprite;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

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

    private boolean hasCollision = true;

    public void update(float dt) {
        applyGravity(dt);
        if (checkGroundCollision()) {
            velocityY = 0;
        }
        
        objectUpdate(dt);

        if (isOutOfBoundsX()) velocityX = 0;
        if (isOutOfBoundsY()) velocityY = 0;

        posX += velocityX;
        posY += velocityY;
    }

    protected abstract void objectUpdate(float dt);

    protected abstract void damage();
    public abstract void kill();

    private void applyGravity(float dt) {
        velocityY += GRAVITY_ACCELERATION * dt;
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }
    }

    private boolean checkGroundCollision() {
        for (float i = 0; i < velocityY + 1 && velocityY > 0; i++) {
            if (i >= velocityY) {
                i = velocityY;
            }
            if (Math.ceil(posY + i) >= ProgramManager.getLevel().getLevelSizeY()) break;
            LinkedList<Block> blocks = new LinkedList<>();
            for (float j = 0; j <= sprite.getWidth(); j++) {
                blocks.add(ProgramManager.getLevel().getBlock((int) (posX + j), (int) (posY + sprite.getHeight() + i)));
            }
            for (Block block : blocks) {
                if (block != null && block.isCollision()) {
                    posY = (float) Math.floor(posY + i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ceilingCollisionCheck() {
        for (float i = 0; i > velocityY - 1 && velocityY < 0; i--) {
            if (i <= velocityY) {
                i = velocityY;
            }
            if (Math.floor(posY + i) < 0) break;
            LinkedList<Block> blocks = new LinkedList<>();
            for (float j = 0; j <= sprite.getWidth(); j++) {
                blocks.add(ProgramManager.getLevel().getBlock((int) (posX + j), (int) (posY + i)));
            }
            for (Block block : blocks) {
                if (block != null && block.isCollision()) {
                    posY = (float) Math.ceil(posY + i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rightBlockCollisionCheck() {
        for (float i = 0; i < velocityX + 1 && velocityX > 0; i++) {
            if (i >= velocityX) {
                i = velocityX;
            }
            if (Math.ceil(posX + i) >= ProgramManager.getLevel().getLevelSizeX()) break;
            LinkedList<Block> blocks = new LinkedList<>();
            for (float j = 0; j < sprite.getHeight(); j++) {
                blocks.add(ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth() + i), (int) (posY + j)));
            }
            for (Block block : blocks) {
                if (block != null && block.isCollision()) {
                    posX = (float) Math.floor(posX + i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean leftBlockCollisionCheck() {
        for (float i = 0; i > velocityX - 1 && velocityX < 0; i--) {
            if (i <= velocityX) {
                i = velocityX;
            }
            if (Math.floor(posX + i) < 0) break;
            LinkedList<Block> blocks = new LinkedList<>();
            for (float j = 0; j < sprite.getHeight(); j++) {
                blocks.add(ProgramManager.getLevel().getBlock((int) (posX + i), (int) (posY + j)));
            }
            for (Block block : blocks) {
                if (block != null && block.isCollision()) {
                    posX = (float) Math.ceil(posX + i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collision(DynamicObject dynamicObject) {
        if (this.sprite == null) {
            return false;
        }
        if (dynamicObject.sprite == null) {
            return false;
        }
        if (!this.hasCollision || !dynamicObject.hasCollision) return false;

        if (this.posX + this.getSizeX() < dynamicObject.posX) return false;
        if (this.posX > dynamicObject.posX + dynamicObject.getSizeX()) return false;
        if (this.posY + this.getSizeY() < dynamicObject.posY) return false;
        if (this.posY > dynamicObject.posY + dynamicObject.getSizeY()) return false;

        return true;
    }

    private boolean isOutOfBoundsX() {
        return posX + velocityX < 0 || Math.ceil(posX) + velocityX > ProgramManager.getLevel().getLevelSizeX() - 1;
    }

    private boolean isOutOfBoundsY() {
        return posY + velocityY < 0 || Math.ceil(posY) + getSizeY() + velocityY > ProgramManager.getLevel().getLevelSizeY() - 1;
    }

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

    public void setSizeY(float sizeY) {
        sprite.setWidth(sizeY);
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

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void serialize() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("assets/" + getAssetDirectory() + "/" + id + getAssetExtension());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public abstract String getObjectCategory();

    protected abstract String getAssetDirectory();
    protected abstract String getAssetExtension();

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
