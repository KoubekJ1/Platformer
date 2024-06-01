package game.level.dynamicobject.enemy;

import game.ProgramManager;
import game.level.Level;
import game.level.blocks.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    @Test
    void kill() {
        Enemy goomba = Goomba.getGoomba(10, 10);
        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 0, 0);
        for (int i = 0; i < 100; i++) {
            level.addBlock(Block.getGround(), i, 20);
        }

        level.addObject(goomba);

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);

        goomba.kill();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(level.getEnemies().contains(goomba));
    }
}