package game.level.character;

import game.ProgramManager;
import game.level.Block;
import renderer.Sprite;
import util.InputManager;

import java.awt.event.KeyEvent;

public abstract class Character {
    public static final int GRAVITY_ACCELERATION = 1;
    private static final float TERMINAL_VELOCITY = 50;
    public static final String CHARACTER_TEXTURES_PATH = "assets/textures/characters/";

    protected Sprite sprite;

    protected float posX;
    protected float posY;
    protected float velocityX;
    protected float velocityY;

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
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.ceil(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.ceil(posY + i));
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
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.floor(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.floor(posY + i));
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
            Block block1 = ProgramManager.getLevel().getBlock((int) (Math.ceil(posX + i)), (int) posY);
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX + i), (int) Math.ceil(posY));
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
            Block block1 = ProgramManager.getLevel().getBlock((int) Math.floor(posX + i), (int) posY);
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.floor(posX + i), (int) Math.ceil(posY));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                posX = (float) Math.ceil(posX + i);
                return true;
            }
        }
        return false;
    }
}