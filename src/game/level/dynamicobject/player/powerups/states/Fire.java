package game.level.dynamicobject.player.powerups.states;

import game.ProgramManager;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.Fireball;
import renderer.Animation;
import renderer.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Fire extends PowerupState {

    private Timer abilityTimer;

    public Fire(Player parentPlayer) {
        super(parentPlayer);
        this.abilityTimer = new Timer(500, e -> abilityTimer.stop());
    }

    @Override
    public void damage() {
        parentPlayer.setPowerupState(new Small(parentPlayer));
    }

    @Override
    public void ability() {
        if (abilityTimer.isRunning()) return;
        Fireball fireball = new Fireball(parentPlayer.getPosX(), parentPlayer.getPosY(), !parentPlayer.getSprite().isMirrored());
        ProgramManager.getLevel().addObject(fireball);
        abilityTimer.start();
        parentPlayer.getSprite().playAnimation("throw");
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
        animations.put("throw", new Animation(PLAYER_TEXTURES_DIRECTORY + "fire/throw.png"));

        return new Sprite(animations, 1, 2);
    }
}
