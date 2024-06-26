package game.level.dynamicobject.player.powerups.states;

import game.level.dynamicobject.player.Player;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Small extends PowerupState {
    public Small(Player parentPlayer) {
        super(parentPlayer, 0);
    }

    @Override
    public void damage() {
        parentPlayer.kill();
    }

    @Override
    public void ability() {

    }

    @Override
    public Sprite getSprite() {
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "small/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/static.png"));
        animations.put("air", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/air.png"));
        animations.put("turn", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/turn.png"));
        animations.put("kill", new Animation(PLAYER_TEXTURES_DIRECTORY + "small/kill.png"));

        return new Sprite(animations, 1, 1);
    }
}
