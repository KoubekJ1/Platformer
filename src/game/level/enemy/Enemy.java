package game.level.enemy;

import game.ProgramManager;
import game.level.Block;
import renderer.Animation;
import renderer.Sprite;
import util.InputManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Enemy {
    private static final float TERMINAL_VELOCITY = 50;
    private static final float GRAVITY_ACCELERATION = 1;

    private Sprite sprite;
    private EnemyBehavior ai;
    private Timer deathTimer;

    private float posX;
    private float posY;
    private float xVelocity;
    private float yVelocity;

    public Enemy(Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = posX;
        this.posY = posY;
        xVelocity = 0;
        yVelocity = 0;
    }

    public void playAnimation(String animation) {
        sprite.playAnimation(animation);
    }

    public void update(float dt) {
        applyGravity(dt);
        groundCollisionCheck();
        ceilingCollisionCheck();
        ai.update(dt);

        posX += xVelocity;
        posY += yVelocity;
    }

    public void damage() {
        ai.damage();
    }

    public void kill() {
        playAnimation("kill");
        deathTimer = new Timer(sprite.getAnimationTimeLeft(), e -> {
            if (e.getSource() == deathTimer) {
                removeFromLevel();
            }
        });
    }

    private void removeFromLevel() {
        ProgramManager.getLevel().getEnemies().remove(this);
    }

    public void setWidth(float width) {
        sprite.setWidth(width);
    }

    public void setHeight(float height) {
        sprite.setHeight(height);
    }

    public float[] getSize() {
        return new float[]{sprite.getWidth(), sprite.getHeight()};
    }

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public boolean isDead() {
        if (deathTimer == null) return false;
        return deathTimer.isRunning();
    }

    private void applyGravity(float dt) {
        float gravityEffectiveness = 1;
        if (yVelocity < 0 && !(InputManager.isKeyPressed(KeyEvent.VK_W) || InputManager.isKeyPressed(KeyEvent.VK_SPACE) || InputManager.isKeyPressed(KeyEvent.VK_UP))) {
            gravityEffectiveness = 3f;
        }
        yVelocity += GRAVITY_ACCELERATION * dt * gravityEffectiveness;
        if (yVelocity > TERMINAL_VELOCITY) {
            yVelocity = TERMINAL_VELOCITY;
        }
    }

    private void groundCollisionCheck() {
        for (float i = 0; i < yVelocity + 1 && yVelocity > 0; i++) {
            if (i >= yVelocity) {
                i = yVelocity;
            }
            if (Math.ceil(posY + i) >= ProgramManager.getLevel().getLevelSizeY()) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.ceil(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.ceil(posY + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                yVelocity = 0;
                posY = (float) Math.floor(posY + i);
                break;
            }
        }
    }

    private void ceilingCollisionCheck() {
        for (float i = 0; i > yVelocity - 1 && yVelocity < 0; i--) {
            if (i <= yVelocity) {
                i = yVelocity;
            }
            if (Math.floor(posY + i) <= 0) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) posX, (int) Math.floor(posY + i));
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(posX), (int) Math.floor(posY + i));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                yVelocity = 0;
                posY = (float) Math.ceil(posY + i);
                break;
            }
        }
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public static Enemy getGoomba(int x, int y) {
        HashMap<String, Animation> animations = new HashMap<>();
        Animation staticAnimation = new Animation("characters/enemies/goomba/static.png");

        ArrayList<String> movementTextures = new ArrayList<>();
        movementTextures.add("characters/enemies/goomba/move1.png");
        movementTextures.add("characters/enemies/goomba/move2.png");
        Animation movementAnimation = new Animation(movementTextures, 80, true);

        ArrayList<String> killTextures = new ArrayList<>();
        killTextures.add("characters/enemies/goomba/kill.png");
        killTextures.add("void.png");
        Animation killAnimation = new Animation(killTextures, new int[]{0, 1, 0, 1}, 40, false);

        animations.put("static", staticAnimation);
        animations.put("move", movementAnimation);
        animations.put("kill", killAnimation);
        return new Enemy(new Sprite(animations, 1, 1), new Goomba(), x, y);
    }
}
