package game.level.character.player.powerups;

import game.ProgramManager;
import game.level.character.DynamicObject;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Fireball extends DynamicObject {

    private static final float SPEED  = 5;
    private static final float JUMP_VELOCITY  = 1;

    public Fireball(float x, float y) {
        this.setPosX(x);
        this.setPosY(y);

        // Just in case there's somehow no animation playing to prevent errors
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

        sprite = new Sprite(animations, 1, 1);
        sprite.playAnimation("move");
    }

    @Override
    protected void objectUpdate(float dt) {
        if (velocityY == 0) jump();
        velocityX = SPEED * dt;
        if (leftBlockCollisionCheck()) kill();
        if (rightBlockCollisionCheck()) kill();
    }

    private void jump() {
        velocityY = JUMP_VELOCITY;
    }

    @Override
    protected void damage() {
        kill();
    }

    @Override
    public void kill() {
        ProgramManager.getLevel().removeObject(this);
    }

    @Override
    protected String getAssetDirectory() {
        return null;
    }

    @Override
    protected String getAssetExtension() {
        return null;
    }
}
