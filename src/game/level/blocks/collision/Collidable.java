package game.level.blocks.collision;

import java.io.Serializable;

@FunctionalInterface
public interface Collidable extends Serializable {
    public void hit(int blockX, int blockY);
}
