package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Koopa extends EnemyBehavior {
    private static final float SPEED = 1;
    private boolean direction;

    public Koopa() {
        this.direction = false;
    }

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
