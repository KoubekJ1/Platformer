package game.level.character.enemy;

import game.ProgramManager;
import game.level.Block;

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
        parentEnemy.kill();
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
