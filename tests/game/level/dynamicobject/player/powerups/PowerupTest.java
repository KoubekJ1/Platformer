package game.level.dynamicobject.player.powerups;

import game.ProgramManager;
import game.level.Level;
import game.level.blocks.Block;
import game.level.dynamicobject.player.powerups.states.Mushroom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerupTest {

    @Test
    void collectPowerup() {
        Powerup powerup = Mushroom.getMushroom(5, 19);
        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 10, 19);
        for (int i = 0; i < 100; i++) {
            level.addBlock(Block.getGround(), i, 20);
        }
        level.addObject(powerup);

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);

        try {
            Thread.sleep(2000);
            assertTrue(level.getPowerups().contains(powerup));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertSame(level.getPlayers().getFirst().getPowerupState().getClass(), Mushroom.class);
        assertFalse(level.getPowerups().contains(powerup));
    }
}