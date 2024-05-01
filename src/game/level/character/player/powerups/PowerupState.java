package game.level.character.player.powerups;

import game.level.character.player.Player;

public abstract class PowerupState {

    private Player parentPlayer;

    public PowerupState(Player parentPlayer) {
        this.parentPlayer = parentPlayer;
    }

    public abstract void damage();

    public abstract void getSprite();
}
