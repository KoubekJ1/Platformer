package game.level.character.player.powerups.states;

import game.ProgramManager;
import game.level.character.player.Player;
import game.level.character.player.powerups.Fireball;
import renderer.Animation;
import renderer.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Fire extends PowerupState {

    public Fire(Player parentPlayer) {
        super(parentPlayer);
    }

    @Override
    public void damage() {
        parentPlayer.setPowerupState(new Small(parentPlayer));
    }

    @Override
    public void ability() {
        Fireball fireball = new Fireball(parentPlayer.getPosX(), parentPlayer.getPosY());
        ProgramManager.getLevel().addObject(fireball);
    }

    @Override
    public Sprite getSprite() {
        HashMap<String, Animation> animations = new HashMap<>();
        ArrayList<String> images = new ArrayList<>();

        images.add(PLAYER_TEXTURES_DIRECTORY + "fire/run1.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "fire/run2.png");
        images.add(PLAYER_TEXTURES_DIRECTORY + "fire/run3.png");

        animations.put("running", new Animation(images, 80, true));
        animations.put("sprinting", new Animation(images, 40, true));
        animations.put("static", new Animation(PLAYER_TEXTURES_DIRECTORY + "fire/static.png"));
        animations.put("air", new Animation(PLAYER_TEXTURES_DIRECTORY + "fire/air.png"));
        animations.put("turn", new Animation(PLAYER_TEXTURES_DIRECTORY + "fire/turn.png"));
        animations.put("kill", new Animation(PLAYER_TEXTURES_DIRECTORY + "fire/kill.png"));

        return new Sprite(animations, 1, 2);
    }
}
