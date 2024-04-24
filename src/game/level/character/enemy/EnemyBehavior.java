package game.level.character.enemy;

public abstract class EnemyBehavior {
    protected Enemy parentEnemy;

    public abstract void update(float dt);

    public abstract void damage();

    public void setParentEnemy(Enemy parentEnemy) {
        this.parentEnemy = parentEnemy;
    }
}
