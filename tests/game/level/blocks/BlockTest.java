package game.level.blocks;

import game.ProgramManager;
import game.level.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class BlockTest {

    @Test
    void hit() {
        Block block = Block.getBrick();
        int blockX = 10;
        int blockY = 10;

        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 0, 0);
        level.addBlock(block, blockX, blockY);

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);

        block.hit(blockX, blockY);

        Assertions.assertNull(level.getBlock(blockX, blockY));
    }
}