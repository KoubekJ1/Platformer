package game.level.dynamicobject.player.powerups.states;

import game.level.dynamicobject.player.Player;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Mushroom extends PowerupState {

    public Mushroom(Player parentPlayer) {
        super(parentPlayer);
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
}
