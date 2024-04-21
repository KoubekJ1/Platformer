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

    private int posX;
    private int posY;

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

    public float getWidth() {
        return sprite.getWidth();
    }

    public void setHeight(float height) {
        sprite.setHeight(height);
    }

    public float getHeight() {
        return sprite.getHeight();
    }
}
