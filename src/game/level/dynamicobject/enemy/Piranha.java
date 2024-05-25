package game.level.dynamicobject.enemy;

import game.ProgramManager;
import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;

public class Piranha extends EnemyBehavior {

    private static float SPEED = 1.5f;

    private Timer activityTimer;

    private boolean direction;
    private boolean moving;
    private float distanceTraveled;

    public Piranha() {
        this.moving = true;
        this.direction = false;
    }

    @Override
    public void update(float dt) {
        if (this.activityTimer == null) {
            this.activityTimer = new Timer(5000, e -> {
                System.out.println(direction);
                if (ProgramManager.getLevel() == null) {
                    activityTimer.stop();
                }
                moving = true;
                distanceTraveled = 0;
                switchDirection();
            });
            this.activityTimer.start();
        }
        if (!moving) {
            return;
        }
        parentEnemy.setVelocityY(SPEED * dt * getDirection());
        distanceTraveled += parentEnemy.getVelocityY();
        if (Math.abs(distanceTraveled) >= parentEnemy.getSizeY()) {
            moving = false;
            parentEnemy.setVelocityY(0);
            parentEnemy.setPosY(parentEnemy.getPosY() - distanceTraveled - parentEnemy.getSizeY() * getDirection() * -1);
        }
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

    @Override
    public void damage() {
        parentEnemy.kill();
    }

    public static Enemy getPiranha(float x, float y) {
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> piranhaImages = new ArrayList<>();
        piranhaImages.add("characters/enemies/piranha/1.png");
        piranhaImages.add("characters/enemies/piranha/2.png");
        Animation piranhaAnimation = new Animation(piranhaImages, 150, true);
        Animation kill = Animation.getKillAnimation("characters/enemies/piranha/1.png");
        animations.put("static", piranhaAnimation);
        animations.put("kill", kill);

        Enemy piranha = new Enemy("Piranha", "piranha", new Sprite(animations, 1, 2), new Piranha());
        piranha.setGravity(false);

        piranha.setPosX(x);
        piranha.setPosY(y);

        return piranha;
    }
}
