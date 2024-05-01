package game.level.character.player.powerups;

import game.ProgramManager;
import game.level.character.DynamicObject;
import renderer.Sprite;

import java.io.IOException;
import java.io.Serializable;

public class Powerup extends DynamicObject implements Serializable {

    private static final String POWERUP_TEXTURES_PATH = "powerups/";

    private static final float POWERUP_SPEED = 1;

    private boolean direction;

    public Powerup(String name, String id, Sprite sprite) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;
    }

    public Powerup(String name, String id, Sprite sprite, int x, int y) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;

        this.posX = x;
        this.posY = y;
    }

    @Override
    protected void objectUpdate(float dt) {
        if (rightBlockCollisionCheck()) {
            switchDirection();
        }
        if (leftBlockCollisionCheck()) {
            switchDirection();
        }

        velocityX = POWERUP_SPEED * getDirection() * dt;
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
    protected void damage() {
        kill();
    }

    @Override
    public void kill() {
        ProgramManager.getLevel().removeObject(this);
    }

    @Override
    protected String getAssetDirectory() {
        return "powerups";
    }

    @Override
    protected String getAssetExtension() {
        return ".powerup";
    }

    public static Powerup getMushroom(int x, int y) {
        Powerup mushroom = new Powerup("Mushroom", "mushroom", new Sprite(POWERUP_TEXTURES_PATH + "mushroom.png", 1, 1));
        try {
            mushroom.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mushroom.setPosX(x);
        mushroom.setPosY(y);

        return mushroom;
    }
}
