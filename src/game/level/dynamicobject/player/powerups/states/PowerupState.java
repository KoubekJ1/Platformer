package game.level.dynamicobject.player.powerups.states;

import game.ProgramManager;
import game.level.dynamicobject.player.Player;
import renderer.Sprite;

import java.io.Serializable;

public abstract class PowerupState implements Serializable {

    protected Player parentPlayer;
    protected static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    private int value;

    public PowerupState(Player parentPlayer, int value) {
        this.parentPlayer = parentPlayer;
        parentPlayer.setSprite(getSprite());
        parentPlayer.enableInvulnerability();
        this.value = value;
    }

    public abstract void damage();

    public abstract void ability();

    public abstract Sprite getSprite();

    public int getValue() {
        return value;
    }
}
