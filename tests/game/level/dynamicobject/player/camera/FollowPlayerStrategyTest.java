package game.level.dynamicobject.player.camera;

import game.ProgramManager;
import game.level.Level;
import game.level.blocks.Block;
import game.level.dynamicobject.enemy.Enemy;
import game.level.dynamicobject.enemy.Goomba;
import game.level.dynamicobject.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FollowPlayerStrategyTest {

    @Test
    void update() {
        // Players created by a level constructor automatically use the follow player strategy
        Level level = new Level("test", "test", 100, 100, Level.SKY_COLOR, 10, 19);
        for (int i = 0; i < 100; i++) {
            level.addBlock(Block.getGround(), i, 20);
        }

        ProgramManager.startProgram(new String[]{});
        ProgramManager.play(level);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Player player = level.getPlayers().getFirst();

        assertEquals(-(10 - 15), player.getCamera().getTransform().getTranslateX());

        player.setPosX(20);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(-(20 - 15), player.getCamera().getTransform().getTranslateX());
    }
}