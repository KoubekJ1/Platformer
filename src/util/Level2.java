package util;

import game.level.Level;
import game.level.blocks.Block;

public class Level2 {
    public static Level getLevel() {
        Level level = new Level("2", "2", 200, 30, Level.SKY_COLOR, 20, 19);

        for (int i = 0; i < 200; i++) {
            for (int j = 20; j < 30; j++) {
                level.addBlock(Block.getGround(), i, j);
            }
        }

        for (int i = 0; i < 20; i++) {
            level.addBlock(new Block("void.png", true), 16, i);
        }

        return level;
    }
}
