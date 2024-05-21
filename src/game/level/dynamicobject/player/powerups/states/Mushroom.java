package game.level.dynamicobject.player.powerups.states;

import game.ProgramManager;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.PickUpable;
import game.level.dynamicobject.player.powerups.Powerup;
import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Mushroom extends PowerupState {

    private static final String POWERUP_TEXTURES_PATH = "powerups/";

    public Mushroom(Player parentPlayer, int value) {
        super(parentPlayer, value);
    }

    @Override
    public void damage() {
        parentPlayer.setPowerupState(new Small(parentPlayer));
    }

    @Override
    public void ability() {

    }

    @Override
    public Sprite getSprite() {
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "large/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "large/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "large/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "large/static.png"));
        animations.put("air", new Animation(PLAYER_TEXTURES_DIRECTORY + "large/air.png"));
        animations.put("turn", new Animation(PLAYER_TEXTURES_DIRECTORY + "large/turn.png"));
        animations.put("kill", new Animation(PLAYER_TEXTURES_DIRECTORY + "large/kill.png"));

        return new Sprite(animations, 1, 2);
    }

    public static Powerup getMushroom() {
        return getMushroom(0, 0);
    }

    public static Powerup getMushroom(int x, int y) {
        Powerup mushroom = new Powerup("Mushroom", "mushroom", new Sprite(POWERUP_TEXTURES_PATH + "mushroom.png", 1, 1), false, new PickUpable() {
            @Override
            public PowerupState getPowerupState(Player player, int value) {
                ProgramManager.getLevel().getScore().addScore(1000);
                return new Mushroom(player, value);
            }
        }, 1);

        mushroom.setPosX(x);
        mushroom.setPosY(y);

        return mushroom;
    }
}
