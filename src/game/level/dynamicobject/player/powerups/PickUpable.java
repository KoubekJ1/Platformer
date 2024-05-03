package game.level.dynamicobject.player.powerups;

import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.states.PowerupState;

import java.io.Serializable;

@FunctionalInterface
public interface PickUpable extends Serializable {
    public PowerupState getPowerupState(Player player);
}
