package game.level.dynamicobject.player.powerups;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.Projectile;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fireball projectiles are the projectiles thrown by the player with the Fire PowerupState
 */
public class Fireball extends Projectile {

    private static final float SPEED = 10;
    private static final float JUMP_VELOCITY = 0.2f;

    /**
     * Creates a new fireball with the given parameters
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param direction the direction of the fireball (1 - right, -1 - left)
     */
    public Fireball(float x, float y, int direction) {
        super(x, y, SPEED, JUMP_VELOCITY, false, true, direction);
    }

    /**
     * Creates a new fireball with the given parameters
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param direction the direction of the fireball (true - right, false - left)
     */
    public Fireball(float x, float y, boolean direction) {
        super(x, y, SPEED, JUMP_VELOCITY, false, true, direction);
    }

    @Override
    protected Sprite getProjectileSprite() {
        Animation staticAnimation = new Animation("projectiles/fireball/1.png");

        ArrayList<String> moveImages = new ArrayList<>();
        moveImages.add("projectiles/fireball/1.png");
        moveImages.add("projectiles/fireball/2.png");
        moveImages.add("projectiles/fireball/3.png");
        moveImages.add("projectiles/fireball/4.png");
        Animation move = new Animation(moveImages, 50, true);

        HashMap<String, Animation> animations = new HashMap<>();
        animations.put("static", staticAnimation);
        animations.put("move", move);

        Sprite projectileSprite = new Sprite(animations, 1, 1);
        return projectileSprite;
    }
}
