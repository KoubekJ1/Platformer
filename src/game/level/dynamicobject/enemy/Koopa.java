package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Koopa is an enemy that behaves the same way as goomba until it gets hit, then it retreats into its shell, and the enemy's EnemyBehavior instance gets changed to KoopaShell
 */
public class Koopa extends EnemyBehavior {
    private static final float SPEED = 1;
    private boolean direction;

    /**
     * Creates a new Koopa that moves to the right
     */
    public Koopa() {
        this.direction = false;
    }

    /**
     * Creates a new Koopa that moves in the given direction
     * @param direction the direction
     */
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
        parentEnemy.getSprite().setMirrored(direction);
        parentEnemy.playAnimation("move");
    }

    @Override
    public void damage() {
        EnemyBehavior shell = new KoopaShell();
        shell.setParentEnemy(parentEnemy);
        parentEnemy.setAi(shell);
        parentEnemy.setSizeY(1);
    }

    /**
     * Gets the direction of the enemy used for multiplying the enemy's velocity
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
     * Switches the enemy's direction
     */
    private void switchDirection() {
        direction = !direction;
    }

    /**
     * Returns a new koopa enemy
     * @param x the x-coordinate of the koopa
     * @param y the y-coordinate of the koopa
     * @return the koopa
     */
    public static Enemy getKoopa(int x, int y) {
        HashMap<String, Animation> animations = new HashMap<>();
        Animation staticAnimation = new Animation("characters/enemies/koopa/static.png");

        ArrayList<String> movementTextures = new ArrayList<>();
        movementTextures.add("characters/enemies/koopa/move1.png");
        movementTextures.add("characters/enemies/koopa/move2.png");
        Animation movementAnimation = new Animation(movementTextures, 160, true);

        animations.put("static", staticAnimation);
        animations.put("move", movementAnimation);
        animations.put("shell", new Animation("characters/enemies/koopa/shell.png"));
        animations.put("kill", Animation.getKillAnimation("characters/enemies/koopa/static.png"));

        Enemy koopa = new Enemy("Koopa", "koopa", new Sprite(animations, 1, 2), new Koopa());

        koopa.setPosX(x);
        koopa.setPosY(y);

        return koopa;
    }
}
