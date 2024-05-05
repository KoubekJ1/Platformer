package game.level.dynamicobject.enemy;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import renderer.Animation;
import renderer.Sprite;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Enemy extends DynamicObject {

    private EnemyBehavior ai;
    private Timer deathTimer;

    public Enemy() {
    }

    public Enemy(String name, String id, Sprite sprite, EnemyBehavior ai) {
        this.name = name;
        this.id = id;
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = 0;
        this.posY = 0;
        velocityX = 0;
        velocityY = 0;
    }

    public Enemy(String name, String id, Sprite sprite, EnemyBehavior ai, int posX, int posY) {
        this.name = name;
        this.id = id;
        this.sprite = sprite;
        this.ai = ai;
        ai.setParentEnemy(this);
        this.posX = posX;
        this.posY = posY;
        velocityX = 0;
        velocityY = 0;
    }

    public void playAnimation(String animation) {
        sprite.playAnimation(animation);
    }

    @Override
    public void objectUpdate(float dt) {
        ai.update(dt);
    }

    @Override
    public void damage() {
        ai.damage();
    }

    @Override
    public void kill() {
        this.setCollision(false);
        playAnimation("kill");
        deathTimer = new Timer(sprite.getAnimationTimeLeft(), e -> {
            if (e.getSource() == deathTimer) {
                sprite.stopBlipping();
                removeFromLevel();
                deathTimer.stop();
            }
        });
        deathTimer.start();
        sprite.blip();
    }

    @Override
    public String getObjectCategory() {
        return "enemy";
    }

    private void removeFromLevel() {
        ProgramManager.getLevel().removeObject(this);
    }

    public boolean isDead() {
        if (deathTimer == null) return false;
        return deathTimer.isRunning();
    }

    public EnemyBehavior getAi() {
        return ai;
    }

    public void setAi(EnemyBehavior ai) {
        this.ai = ai;
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
        animations.put("kill", Animation.getKillAnimation("characters/enemies/koopa/shell.png"));

        Enemy koopa = new Enemy("Koopa", "koopa", new Sprite(animations, 1, 2), new Koopa());
        try {
            koopa.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        koopa.setPosX(x);
        koopa.setPosY(y);

        return koopa;
    }

    @Override
    protected String getAssetDirectory() {
        return "enemies";
    }

    @Override
    protected String getAssetExtension() {
        return ".enemy";
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "ai=" + ai +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", posX=" + posX +
                ", posY=" + posY +
                ", velocityX=" + velocityX +
                ", velocityY=" + velocityY +
                '}';
    }
}
