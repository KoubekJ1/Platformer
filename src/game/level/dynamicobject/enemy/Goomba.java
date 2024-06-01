package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Goomba is an enemy that moves across the ground until it hits the wall, then it switches it direction
 * The goomba dies when it is hit by the player
 */
public class Goomba extends EnemyBehavior {
    private static final float SPEED = 1;
    private boolean direction;

    /**
     * Creates a new Goomba that moves to the right
     */
    public Goomba() {
        this.direction = false;
    }

    /**
     * Creates a new Goomba that moves in the given direction
     * @param direction the direction
     */
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

    /**
     * Gets the direction of the Goomba used for multiplying the enemy's velocity
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
     * Switches the Goomba's direction
     */
    private void switchDirection() {
        direction = !direction;
    }

    /**
     * Returns a new Goomba enemy
     * @param x the x-coordinate of the Goomba
     * @param y the y-coordinate of the Goomba
     * @return the Goomba
     */
    public static Enemy getGoomba(int x, int y) {
        HashMap<String, Animation> animations = new HashMap<>();
        Animation staticAnimation = new Animation("characters/enemies/goomba/static.png");

        ArrayList<String> movementTextures = new ArrayList<>();
        movementTextures.add("characters/enemies/goomba/move1.png");
        movementTextures.add("characters/enemies/goomba/move2.png");
        Animation movementAnimation = new Animation(movementTextures, 160, true);

        animations.put("static", staticAnimation);
        animations.put("move", movementAnimation);
        animations.put("kill", Animation.getKillAnimation("characters/enemies/goomba/kill.png"));

        Enemy goomba = new Enemy("Goomba", "goomba", new Sprite(animations, 1, 1), new Goomba());

        goomba.setPosX(x);
        goomba.setPosY(y);

        return goomba;
    }
}
