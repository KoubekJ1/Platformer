package game.level.enemy;

public class Goomba extends EnemyBehavior {
    private static final float SPEED = 1;
    private boolean direction;

    public Goomba() {
        this.direction = true;
    }

    public Goomba(boolean direction) {
        this.direction = direction;
    }

    @Override
    public void update(float dt) {
        if (parentEnemy.isDead()) return;
        parentEnemy.setPosition(parentEnemy.getPosition()[0] + SPEED * dt * getDirection(), parentEnemy.getPosition()[1]);
    }

    @Override
    public void damage() {
        parentEnemy.kill();
    }

    private int getDirection() {
        if (direction) {
            return 1;
        } else {
            return -1;
        }
    }
}
