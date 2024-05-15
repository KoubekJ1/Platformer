package util;

import game.level.Level;
import game.level.blocks.Block;
import game.level.dynamicobject.enemy.Goomba;

public class Level2 {
    public static Level getLevel() {
        Level level = new Level("2", "2", 45, 100, Level.SKY_COLOR, 4, 89);

        int islandWidth = 20;
        for (int i = 0; i < level.getLevelSizeX(); i += islandWidth + 5) {
            for (int k = 0; k < islandWidth; k++) {
                for (int j = 90; j < 90 + (islandWidth/2)- Math.abs(k - Math.ceil((double) islandWidth / 2)); j++) {
                    level.addBlock(Block.getGround(), i + k, j);
                }
            }
        }

        for (int i = 0; i < level.getLevelSizeX(); i += islandWidth + 5) {
            int counter = 0;
            for (int j = 89; j > 20; j--) {
                Block block;
                if (counter >= 3) {
                    block = Block.getUnbreakableBlock();
                    counter = 0;
                } else {
                    block = Block.getBrick();
                    counter++;
                }
                level.addBlock(block, i + 2, j);
                level.addBlock(block, i + islandWidth - 2, j);
            }
        }

        level.addObject(Goomba.getGoomba(13, 89));

        return level;
    }
}
