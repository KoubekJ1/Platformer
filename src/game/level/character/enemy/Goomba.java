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
        rightBlockCollisionCheck();
        leftBlockCollisionCheck();
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

    private void rightBlockCollisionCheck() {
        for (float i = 0; i < parentEnemy.getVelocityX() + 1 && parentEnemy.getVelocityX() > 0; i++) {
            if (i >= parentEnemy.getVelocityX()) {
                i = parentEnemy.getVelocityX();
            }
            if (Math.ceil(parentEnemy.getPosition()[0] + i) >= ProgramManager.getLevel().getLevelSizeX()) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) (Math.ceil(parentEnemy.getPosition()[0] + i)), (int) parentEnemy.getPosition()[1]);
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.ceil(parentEnemy.getPosition()[0] + i), (int) Math.ceil(parentEnemy.getPosition()[1]));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                switchDirection();
                break;
            }
        }
    }

    private void leftBlockCollisionCheck() {
        for (float i = 0; i > parentEnemy.getVelocityX() - 1 && parentEnemy.getVelocityX() < 0; i--) {
            if (i <= parentEnemy.getVelocityX()) {
                i = parentEnemy.getVelocityX();
            }
            if (Math.floor(parentEnemy.getPosition()[1] + i) <= 0) break;
            Block block1 = ProgramManager.getLevel().getBlock((int) Math.floor(parentEnemy.getPosition()[0] + i), (int) parentEnemy.getPosition()[1]);
            Block block2 = ProgramManager.getLevel().getBlock((int) Math.floor(parentEnemy.getPosition()[0] + i), (int) Math.ceil(parentEnemy.getPosition()[1]));
            if ((block1 != null && block1.isCollision()) || (block2 != null && block2.isCollision())) {
                switchDirection();
                break;
            }
        }
    }
}
