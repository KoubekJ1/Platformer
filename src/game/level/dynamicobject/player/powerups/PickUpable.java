package game.level.dynamicobject.player.powerups;

import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.states.PowerupState;

import java.io.Serializable;

/**
 * PickUpable serves as the action that carries out when a pick-up is collected
 */
@FunctionalInterface
public interface PickUpable extends Serializable {
    /**
     * Returns the PowerupState of the given power-up
     * @param player the player who collected the power-up
     * @param value the value of the collected power-up
     * @return the PowerupState instance
     */
    public PowerupState getPowerupState(Player player, int value);
}
