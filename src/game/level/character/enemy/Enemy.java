package game.level.character.enemy;

import game.ProgramManager;
import game.level.Block;
import game.level.character.Character;
import renderer.Animation;
import renderer.Sprite;
import util.InputManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Enemy extends Character {

    private EnemyBehavior ai;
    private Timer deathTimer;

    public Enemy() {
    }

    public Enemy(Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = posX;
        this.posY = posY;
        velocityX = 0;
        velocityY = 0;
    }

    public void playAnimation(String animation) {
        sprite.playAnimation(animation);
    }

    @Override
    public void characterUpdate(float dt) {
        ai.update(dt);
    }

    @Override
    public void damage() {
        ai.damage();
    }

    @Override
    public void kill() {
        this.setCollision(false);
        playAnimation("kill");
        deathTimer = new Timer(sprite.getAnimationTimeLeft(), e -> {
            if (e.getSource() == deathTimer) {
                removeFromLevel();
            }
        });
        deathTimer.start();
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

    public static Enemy getGoomba(int x, int y) {
        HashMap<String, Animation> animations = new HashMap<>();
        Animation staticAnimation = new Animation("characters/enemies/goomba/static.png");

        ArrayList<String> movementTextures = new ArrayList<>();
        movementTextures.add("characters/enemies/goomba/move1.png");
        movementTextures.add("characters/enemies/goomba/move2.png");
        Animation movementAnimation = new Animation(movementTextures, 160, true);

        ArrayList<String> killTextures = new ArrayList<>();
        killTextures.add("characters/enemies/goomba/kill.png");
        killTextures.add("void.png");
        Animation killAnimation = new Animation(killTextures, new int[]{0, 1, 0, 1, 0, 1, 0, 1}, 100, false);

        animations.put("static", staticAnimation);
        animations.put("move", movementAnimation);
        animations.put("kill", killAnimation);
        return new Enemy(new Sprite(animations, 1, 1), new Goomba(), x, y);
    }
}
