package game.level;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.dynamicobject.enemy.Enemy;
import game.level.dynamicobject.enemy.Goomba;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @Test
    void addObject() {
        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 0, 0);
        for (int i = 0; i < 100; i++) {
            level.addBlock(Block.getGround(), i, 20);
        }

        Enemy goomba = Goomba.getGoomba(20, 10);
        level.addObject(goomba);

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(level.getEnemies().contains(goomba));
    }

    @Test
    void removeObject() {
        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 0, 0);
        for (int i = 0; i < 100; i++) {
            level.addBlock(Block.getGround(), i, 20);
        }

        Enemy goomba = Goomba.getGoomba(20, 10);
        level.addObject(goomba);

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        level.removeObject(goomba);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(level.getEnemies().contains(goomba));
    }
}