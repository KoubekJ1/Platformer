package game.level.blocks.collision;

import java.io.Serializable;

/**
 * Collidable represents the action that is performed when the given block is hit
 */
@FunctionalInterface
public interface Collidable extends Serializable {
    /**
     * Carries out the action that is performed when the given block is hit
     * @param blockX the x-coordinate of the block
     * @param blockY the y-coordinate of the block
     */
    public void hit(int blockX, int blockY);
}
