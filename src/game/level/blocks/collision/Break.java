package game.level.blocks.collision;

import game.ProgramManager;

/**
 * Breakable blocks are blocks that are removed when they are hit
 */
public class Break implements Collidable {
    @Override
    public void hit(int blockX, int blockY) {
        ProgramManager.getLevel().removeBlock(blockX, blockY);
    }
}
