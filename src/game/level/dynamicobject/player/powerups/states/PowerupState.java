package game.level.dynamicobject.player.powerups.states;

import game.level.dynamicobject.player.Player;
import renderer.Sprite;

import java.io.Serializable;

public abstract class PowerupState implements Serializable {

    protected Player parentPlayer;
    protected static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    public PowerupState(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
        parentPlayer.setSprite(getSprite());
        parentPlayer.enableInvulnerability();
    }

    public abstract void damage();

    public abstract void ability();

    public abstract Sprite getSprite();
}
