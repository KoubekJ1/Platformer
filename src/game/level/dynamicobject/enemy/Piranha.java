package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Timer;

public class Piranha extends EnemyBehavior {

    private static float SPEED = 1;

    private Timer activityTimer;

    private boolean direction;
    private boolean moving;
    private float distanceTraveled;

    public Piranha() {
        this.activityTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moving = true;
                distanceTraveled = 0;
                switchDirection();
            }
        });
    }

    @Override
    public void update(float dt) {
        if (!moving) return;
        parentEnemy.setVelocityY(SPEED * dt);
        if (distanceTraveled + parentEnemy.getVelocityY() >= parentEnemy.getSizeY()) {
            moving = false;
            parentEnemy.setVelocityY(parentEnemy.getSizeY() - distanceTraveled);
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

    }

    public static Enemy getPiranha(float x, float y) {
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> piranhaImages = new ArrayList<>();
        piranhaImages.add("characters/enemies/piranha/1.png");
        piranhaImages.add("characters/enemies/piranha/2.png");
        Animation piranhaAnimation = new Animation(piranhaImages, 50, true);

        Enemy piranha = new Enemy("Piranha", "piranha", new Sprite(animations, 1, 2), new Piranha());
        try {
            piranha.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        piranha.setPosX(x);
        piranha.setPosY(y);

        return piranha;
    }
}
