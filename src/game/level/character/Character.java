package game.level.character;

import game.ProgramManager;
import game.level.Block;
import renderer.Sprite;
import util.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public abstract class Character {
    public static final int GRAVITY_ACCELERATION = 1;
    private static final float TERMINAL_VELOCITY = 50;
    public static final String CHARACTER_TEXTURES_PATH = "assets/textures/characters/";

    protected Sprite sprite;

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
        
        characterUpdate(dt);

        posX += velocityX;
        posY += velocityY;
    }

    protected abstract void characterUpdate(float dt);

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
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) (posY + sprite.getHeight() + i));
            int roundingFactor = 0;
            if ((int) posX == (int) Math.ceil(posX)) roundingFactor = -1;
            Block block2 = ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth()), (int) (posY + sprite.getHeight() + roundingFactor + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                posY = (float) Math.floor(posY + i);
                return true;
            }
        }
        return false;
    }

    public boolean ceilingCollisionCheck() {
        for (float i = 0; i > velocityY - 1 && velocityY < 0; i--) {
            if (i <= velocityY) {
                i = velocityY;
            }
            if (Math.floor(posY + i) <= 0) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) (posY + i));
            int roundingFactor = 0;
            if ((int) posX == (int) Math.ceil(posX)) roundingFactor = -1;
            Block block2 = ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth()), (int) (posY + roundingFactor + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                posY = (float) Math.ceil(posY + i);
                return true;
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
            Block block1 = ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth() + i), (int) posY);
            int roundingFactor = 0;
            if ((int) posY == (int) Math.ceil(posY)) roundingFactor = -1;
            Block block2 = ProgramManager.getLevel().getBlock((int) (posX + sprite.getWidth() + i), (int) (posY + sprite.getHeight() + roundingFactor));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                posX = (float) Math.floor(posX + i);
                return true;
            }
        }
        return false;
    }

    public boolean leftBlockCollisionCheck() {
        for (float i = 0; i > velocityX - 1 && velocityX < 0; i--) {
            if (i <= velocityX) {
                i = velocityX;
            }
            if (Math.floor(posY + i) <= 0) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) (posX + i), (int) posY);
            int roundingFactor = 0;
            if ((int) posY == (int) Math.ceil(posY)) roundingFactor = -1;
            Block block2 = ProgramManager.getLevel().getBlock((int) (posX + i), (int) (posY + sprite.getHeight() + roundingFactor));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                posX = (float) Math.ceil(posX + i);
                return true;
            }
        }
        return false;
    }

    public boolean collision(Character character) {
        if (this.sprite == null) {
            return false;
        }
        if (character.sprite == null) {
            return false;
        }
        if (!this.hasCollision || !character.hasCollision) return false;

        if (this.posX + this.getSizeX() < character.posX) return false;
        if (this.posX > character.posX + character.getSizeX()) return false;
        if (this.posY + this.getSizeY() < character.posY) return false;
        if (this.posY > character.posY + character.getSizeY()) return false;

        return true;
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

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
