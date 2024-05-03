package game.level.dynamicobject.player.powerups;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.Projectile;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Fireball extends Projectile {

    public Fireball(float x, float y, int direction) {
        super(x, y, 5, 0.2f, false, true, direction);
    }

    public Fireball(float x, float y, boolean direction) {
        super(x, y, 5, 0.2f, false, true, direction);
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
