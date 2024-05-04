package game.level.dynamicobject.enemy;

public class KoopaShell extends EnemyBehavior {

    private static final float SPEED = 5;
    private boolean direction;
    private boolean moving;

    public KoopaShell() {
        parentEnemy.setSizeY(1);
        this.direction = true;
        this.moving = false;
    }

    public KoopaShell(boolean direction) {
        parentEnemy.setSizeY(1);
        this.direction = direction;
        this.moving = false;
    }

    @Override
    public void update(float dt) {
        if (!moving) return;
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
        this.moving = !moving;
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
