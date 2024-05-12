package game.level.blocks.collision;

import game.ProgramManager;

public class Break implements Collidable {
    @Override
    public void hit(int blockX, int blockY) {
        ProgramManager.getLevel().removeBlock(blockX, blockY);
    }
}
