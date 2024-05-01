package game.level.character.player.powerups;

import game.level.character.player.Player;
import game.level.character.player.powerups.states.PowerupState;

@FunctionalInterface
public interface PickUpable {
    public PowerupState getPowerupState(Player player);
}
