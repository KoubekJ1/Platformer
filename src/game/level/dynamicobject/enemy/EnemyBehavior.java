package game.level.dynamicobject.enemy;

import java.io.Serializable;

/**
 * EnemyBehavior defines how an enemy behaves through the update() method
 */
public abstract class EnemyBehavior implements Serializable {
    protected Enemy parentEnemy;

    /**
     * Updates the enemy
     * @param dt the time between frames in seconds
     */
    public abstract void update(float dt);

    /**
     * Damages the enemy
     */
    public abstract void damage();

    /**
     * Sets the parent enemy of the EnemyBehavior instance
     * @param parentEnemy the parent enemy
     */
    public void setParentEnemy(Enemy parentEnemy) {
        this.parentEnemy = parentEnemy;
    }
}
