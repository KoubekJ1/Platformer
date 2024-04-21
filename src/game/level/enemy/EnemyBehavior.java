package game.level.enemy;

public abstract class EnemyBehavior {
    protected Enemy parentEnemy;

    public EnemyBehavior(Enemy parentEnemy) {
        this.parentEnemy = parentEnemy;
    }

    public abstract void update();
}
