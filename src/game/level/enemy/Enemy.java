package game.level.enemy;

import game.ProgramManager;
import renderer.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enemy {
    private Sprite sprite;
    private EnemyBehavior ai;
    private Timer deathTimer;

    private float posX;
    private float posY;

    public Enemy(Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = posX;
        this.posY = posY;
    }

    public void playAnimation(String animation) {
        sprite.playAnimation(animation);
    }

    public void update(float dt) {
        ai.update(dt);
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

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public boolean isDead() {
        if (deathTimer == null) return false;
        return deathTimer.isRunning();
    }
}
