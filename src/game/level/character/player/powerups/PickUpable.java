package game.level.character.player.powerups;

import game.level.character.player.Player;
import game.level.character.player.powerups.states.PowerupState;

import java.io.Serializable;

@FunctionalInterface
public interface PickUpable extends Serializable {
    public PowerupState getPowerupState(Player player);
}
