package game.level.dynamicobject.enemy;

public class Koopa extends EnemyBehavior {
    private static final float SPEED = 1;
    private boolean direction;

    public Koopa() {
        this.direction = true;
    }

    public Koopa(boolean direction) {
        this.direction = direction;
    }

    @Override
    public void update(float dt) {
        if (parentEnemy.isDead()) return;
        if (parentEnemy.rightBlockCollisionCheck()) {
            switchDirection();
        }
        if (parentEnemy.leftBlockCollisionCheck()) {
            switchDirection();
        }
        parentEnemy.setVelocityX(SPEED * dt * getDirection());
        parentEnemy.playAnimation("move");
    }

    @Override
    public void damage() {
        EnemyBehavior shell = new KoopaShell();
        shell.setParentEnemy(parentEnemy);
        parentEnemy.setAi(shell);
    }

    private int getDirection() {
        if (direction) {
            return 1;
        } else {
            return -1;
        }
    }

    private void switchDirection() {
        direction = !direction;
    }
}
