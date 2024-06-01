package game.level.dynamicobject.player.powerups;

import game.ProgramManager;
import game.level.dynamicobject.DynamicObject;
import game.level.dynamicobject.player.Player;
import game.level.dynamicobject.player.powerups.states.Fire;
import game.level.dynamicobject.player.powerups.states.Mushroom;
import game.level.dynamicobject.player.powerups.states.PowerupState;
import renderer.Animation;
import renderer.Sprite;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Power-ups are small objects that the player can collect for increased health and sometimes even gain some special powers
 */
public class Powerup extends DynamicObject implements Serializable {

    private static final String POWERUP_TEXTURES_PATH = "powerups/";
    private static final float POWERUP_SPEED = 1;

    private boolean direction;
    private boolean isStatic;
    private PickUpable pickUpable;
    private int value;

    /**
     * Creates a new power-up without specifying coordinates (used for lucky blocks)
     * @param name the name of the power-up
     * @param id the id of the power-up (used for serialization)
     * @param sprite the power-up's sprite
     * @param isStatic whether the power-up stays in place
     * @param pickUpable the action to carry out upon collecting the power-up
     * @param value the value of the power-up (to prevent power-up downgrading)
     */
    public Powerup(String name, String id, Sprite sprite, boolean isStatic, PickUpable pickUpable, int value) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;
        this.pickUpable = pickUpable;
        this.isStatic = isStatic;
        this.value = value;
    }

    /**
     * Creates a new power-up without specifying coordinates (used for lucky blocks)
     * @param name the name of the power-up
     * @param id the id of the power-up (used for serialization)
     * @param sprite the power-up's sprite
     * @param isStatic whether the power-up stays in place
     * @param pickUpable the action to carry out upon collecting the power-up
     * @param value the value of the power-up (to prevent power-up downgrading)
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Powerup(String name, String id, Sprite sprite, PickUpable pickUpable, boolean isStatic, int value, int x, int y) {
        this.name = name;
        this.id = id;
        this.direction = true;
        this.sprite = sprite;
        this.pickUpable = pickUpable;
        this.isStatic = isStatic;
        this.value = value;

        this.posX = x;
        this.posY = y;
    }

    @Override
    protected void objectUpdate(float dt) {
        if (isStatic) return;
        if (rightBlockCollisionCheck()) {
            switchDirection();
        }
        if (leftBlockCollisionCheck()) {
            switchDirection();
        }

        velocityX = POWERUP_SPEED * getDirection() * dt;
    }

    /**
     * Returns the direction in which the power-up is moving to use for multiplying
     * @return the direction
     */
    private int getDirection() {
        if (direction) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Switches the power-up's direction
     */
    private void switchDirection() {
        direction = !direction;
    }

    @Override
    protected void damage() {
        kill();
    }

    @Override
    public void kill() {
        ProgramManager.getLevel().removeObject(this);
    }

    @Override
    public String getObjectCategory() {
        return "powerup";
    }

    @Override
    protected String getAssetDirectory() {
        return "powerups";
    }

    @Override
    protected String getAssetExtension() {
        return ".powerup";
    }

    /**
     * Applies the power-up effects and removes it from the level
     * @param player the player who picked up the power-up
     * @return the PowerupState to be given to the player
     */
    public PowerupState collectPowerup(Player player) {
        ProgramManager.getLevel().removeObject(this);
        if (player.getPowerupState().getValue() < this.value) return pickUpable.getPowerupState(player, value);
        else return player.getPowerupState();
    }
}
