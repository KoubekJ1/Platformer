package game.level.dynamicobject.player.powerups;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.states.Fire;
import game.level.dynamicobject.player.powerups.states.Mushroom;
import game.level.dynamicobject.player.powerups.states.PowerupState;
import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Powerup extends DynamicObject implements Serializable {

    private static final String POWERUP_TEXTURES_PATH = "powerups/";
    private static final float POWERUP_SPEED = 1;

    private boolean direction;
    private boolean isStatic;
    private PickUpable pickUpable;

    public Powerup(String name, String id, Sprite sprite, boolean isStatic, PickUpable pickUpable) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;
        this.pickUpable = pickUpable;
        this.isStatic = isStatic;
    }

    public Powerup(String name, String id, Sprite sprite, PickUpable pickUpable, boolean isStatic, int x, int y) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;
        this.pickUpable = pickUpable;
        this.isStatic = isStatic;

        this.posX = x;
        this.posY = y;
    }

    @Override
    protected void objectUpdate(float dt) {
        if (isStatic) return;
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
    public String getObjectCategory() {
        return "powerup";
    }

    @Override
    protected String getAssetDirectory() {
        return "powerups";
    }

    @Override
    protected String getAssetExtension() {
        return ".powerup";
    }

    public PowerupState collectPowerup(Player player) {
        ProgramManager.getLevel().removeObject(this);
        return pickUpable.getPowerupState(player);
    }

    public static Powerup getMushroom(int x, int y) {
        Powerup mushroom = new Powerup("Mushroom", "mushroom", new Sprite(POWERUP_TEXTURES_PATH + "mushroom.png", 1, 1), false, new PickUpable() {
            @Override
            public PowerupState getPowerupState(Player player) {
                return new Mushroom(player);
            }
        });
        try {
            mushroom.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mushroom.setPosX(x);
        mushroom.setPosY(y);

        return mushroom;
    }

    public static Powerup getFireflower(int x, int y) {
        ArrayList<String> fireflowerImages = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            fireflowerImages.add(POWERUP_TEXTURES_PATH + "fireflower/" + i + ".png");
        }
        Animation fireflowerAnimation = new Animation(fireflowerImages, 50, true);
        HashMap<String, Animation> fireFlowerAnimations = new HashMap<>();
        fireFlowerAnimations.put("static", fireflowerAnimation);
        Sprite fireflowerSprite = new Sprite(fireFlowerAnimations, 1, 1);
        Powerup fireflower = new Powerup("Fireflower", "fireflower", fireflowerSprite, true, new PickUpable() {
            @Override
            public PowerupState getPowerupState(Player player) {
                return new Fire(player);
            }
        });
        try {
            fireflower.serialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fireflower.setPosX(x);
        fireflower.setPosY(y);

        return fireflower;
    }
}
