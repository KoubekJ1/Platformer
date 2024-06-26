package game.level.dynamicobject.enemy;

import game.ProgramManager;
import game.level.Score;

/**
 * A koopa that has retracted to its shell
 * When the shell is hit, it starts speeding across the ground bouncing back and forth, until the player stops it by hitting it again
 */
public class KoopaShell extends EnemyBehavior {

    private static final float SPEED = 5;
    private boolean direction;
    private boolean moving;

    /**
     * Creates a new KoopaShell
     */
    public KoopaShell() {
        this.direction = true;
        this.moving = false;
    }

    /**
     * Creates a new KoopaShell moving in the given direction
     * @param direction the direction
     */
    public KoopaShell(boolean direction) {
        this.direction = direction;
        this.moving = false;
    }

    @Override
    public void update(float dt) {
        parentEnemy.playAnimation("shell");
        if (!moving) {
            parentEnemy.setVelocityX(0);
            ProgramManager.getLevel().getScore().resetMultiplier(parentEnemy);
            return;
        }
        if (parentEnemy.isDead()) return;
        if (parentEnemy.rightBlockCollisionCheck()) {
            switchDirection();
        }
        if (parentEnemy.leftBlockCollisionCheck()) {
            switchDirection();
        }
        parentEnemy.setVelocityX(SPEED * dt * getDirection());

        for (Enemy enemy : ProgramManager.getLevel().getEnemies()) {
            if (parentEnemy.collision(enemy)) {
                enemy.kill();
                int score = Score.KOOPA_DAMAGE_SCORE * ProgramManager.getLevel().getScore().getMultiplier(parentEnemy);
                ProgramManager.getLevel().getScore().addScore(score);
                ProgramManager.getLevel().getScore().increaseMultiplier(parentEnemy);
            }
        }
    }

    @Override
    public void damage() {
        this.moving = !moving;
    }

    /**
     * Gets the direction of the shell used for multiplying the enemy's velocity
     * @return the direction
     */
    private int getDirection() {
        if (direction) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Switches the shell's direction
     */
    private void switchDirection() {
        direction = !direction;
    }
}
