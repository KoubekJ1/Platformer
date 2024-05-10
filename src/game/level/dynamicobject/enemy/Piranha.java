package game.level.dynamicobject.enemy;

import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Piranha extends EnemyBehavior {
    @Override
    public void update(float dt) {

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
