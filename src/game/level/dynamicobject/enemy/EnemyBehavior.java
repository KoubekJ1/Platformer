package game.level.dynamicobject.enemy;

import java.io.Serializable;

public abstract class EnemyBehavior implements Serializable {
    protected Enemy parentEnemy;

    public abstract void update(float dt);

    public abstract void damage();

    public void setParentEnemy(Enemy parentEnemy) {
        this.parentEnemy = parentEnemy;
    }
}
