package game.level.character.player.powerups.states;

import game.level.character.player.Player;
import renderer.Sprite;

public abstract class PowerupState {

    protected Player parentPlayer;
    protected static final String PLAYER_TEXTURES_DIRECTORY = "characters/player/";

    public PowerupState(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
        if (parentPlayer.getSprite() != null) {
            float sizeDifference = parentPlayer.getSize()[1] - getSprite().getHeight();
            parentPlayer.setPosY(parentPlayer.getPosY() + sizeDifference);
        }
        parentPlayer.setSprite(getSprite());
    }

    public abstract void damage();

    public abstract Sprite getSprite();
}
