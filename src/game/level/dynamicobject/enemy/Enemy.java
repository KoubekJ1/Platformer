package game.level.dynamicobject.enemy;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import renderer.Animation;
import renderer.Sprite;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Enemies are characters that pose a threat to the player
 * Their behavior is specified in their EnemyBehavior instance
 */
public class Enemy extends DynamicObject {

    private EnemyBehavior ai;
    private Timer deathTimer;

    /**
     * Creates a new enemy with the given parameters
     * @param name the name of the enemy
     * @param id the id of the enemy (used for serialization)
     * @param sprite the enemy's sprite
     * @param ai the enemy's EnemyBehavior instance
     */
    public Enemy(String name, String id, Sprite sprite, EnemyBehavior ai) {
        this.name = name;
        this.id = id;
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = 0;
        this.posY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Creates a new enemy with the given parameters including its position
     * @param name the name of the enemy
     * @param id the id of the enemy (used for serialization)
     * @param sprite the enemy's sprite
     * @param ai the enemy's EnemyBehavior instance
     * @param posX the x-coordinate
     * @param posY the y-coordinate
     */
    public Enemy(String name, String id, Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.name = name;
        this.id = id;
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = posX;
        this.posY = posY;
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Plays an animation in the enemy's sprite
     * @param animation the animation
     */
    public void playAnimation(String animation) {
        sprite.playAnimation(animation);
    }

    @Override
    public void objectUpdate(float dt) {
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
                sprite.stopBlipping();
                removeFromLevel();
                deathTimer.stop();
            }
        });
        deathTimer.start();
        sprite.blip();
    }

    @Override
    public String getObjectCategory() {
        return "enemy";
    }

    /**
     * Removes the enemy from the level
     */
    private void removeFromLevel() {
        ProgramManager.getLevel().removeObject(this);
    }

    /**
     * Gets whether the enemy is dead
     * @return whether the enemy is dead
     */
    public boolean isDead() {
        if (deathTimer == null) return false;
        return deathTimer.isRunning();
    }

    public EnemyBehavior getAi() {
        return ai;
    }

    public void setAi(EnemyBehavior ai) {
        this.ai = ai;
    }

    @Override
    protected String getAssetDirectory() {
        return "enemies";
    }

    @Override
    protected String getAssetExtension() {
        return ".enemy";
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "ai=" + ai +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                '}';
    }
}
