package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        try {
            goomba.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        goomba.setPosX(x);
        goomba.setPosY(y);

        return goomba;
    }
}
