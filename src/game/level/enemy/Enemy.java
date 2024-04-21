package game.level.enemy;

import renderer.Sprite;

import java.awt.*;

public class Enemy {
    private Sprite sprite;
    private EnemyBehavior ai;

    private int posX;
    private int posY;

    public Enemy(Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.sprite = sprite;
        this.ai = ai;
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
