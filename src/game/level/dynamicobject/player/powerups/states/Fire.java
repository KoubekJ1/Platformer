package game.level.dynamicobject.player.powerups.states;

import game.ProgramManager;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.Fireball;
import game.level.dynamicobject.player.powerups.PickUpable;
import game.level.dynamicobject.player.powerups.Powerup;
import renderer.Animation;
import renderer.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Fire extends PowerupState {

    private static final String POWERUP_TEXTURES_PATH = "powerups/";

    private Timer abilityTimer;

    public Fire(Player parentPlayer) {
        super(parentPlayer);
        this.abilityTimer = new Timer(500, e -> abilityTimer.stop());
    }

    @Override
    public void damage() {
        parentPlayer.setPowerupState(new Mushroom(parentPlayer));
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

    @Override
    public int getValue() {
        return 2;
    }

    public static Powerup getFireflower() {
        return getFireflower(0, 0);
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

        fireflower.setPosX(x);
        fireflower.setPosY(y);

        return fireflower;
    }
}
