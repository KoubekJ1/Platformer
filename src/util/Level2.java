package util;

import game.level.Level;
import game.level.blocks.Block;
import game.level.blocks.collision.LuckyBlock;
import game.level.dynamicobject.enemy.Goomba;
import game.level.dynamicobject.enemy.Koopa;
import game.level.dynamicobject.enemy.Piranha;
import game.level.dynamicobject.player.powerups.states.Mushroom;

import javax.swing.*;

public class Level2 {
    public static Level getLevel() {
        Level level = new Level("2", "2", new ImageIcon("assets/textures/level2.png"),45, 100, Level.SKY_COLOR, 4, 89);

        int islandWidth = 20;
        for (int i = 0; i < level.getLevelSizeX(); i += islandWidth + 5) {
            for (int k = 0; k < islandWidth; k++) {
                for (int j = 90; j < 90 + (islandWidth/2)- Math.abs(k - Math.ceil((double) islandWidth / 2)); j++) {
                    level.addBlock(Block.getGround(), i + k, j);
                }
            }
        }

        for (int i = 0; i < level.getLevelSizeX(); i += islandWidth + 5) {
            boolean side = false;
            int counter = 0;
            for (int j = 89; j > 43; j--) {
                Block block;
                if (counter >= 4) {
                    block = Block.getUnbreakableBlock();
                    for (int k = i + 3; k < i + islandWidth - 2; k++) {
                        if (i == 0) {
                            if (j == 50 || j == 60 || j == 65) {
                                continue;
                            }
                        }
                        level.addBlock(Block.getGround(), k, j);
                        if (!side) {
                            if (k == i + 5 || k == i + 6) level.removeBlock(k, j);
                            level.addBlock(Block.getUnbreakableBlock(), i + 8, j - 1);
                        } else {
                            if (k == i + islandWidth - 5 || k == i + islandWidth - 6) level.removeBlock(k, j);
                            level.addBlock(Block.getUnbreakableBlock(), i + islandWidth - 8, j - 1);
                        }
                    }
                    side = !side;
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

        level.addObject(Goomba.getGoomba(13, 84));
        level.addObject(Goomba.getGoomba(15, 84));

        level.addObject(Goomba.getGoomba(5, 79));
        level.addObject(Goomba.getGoomba(8, 79));
        level.addObject(Goomba.getGoomba(11, 79));

        level.addObject(Koopa.getKoopa(10, 73));
        level.addObject(Goomba.getGoomba(13, 74));
        level.addObject(Goomba.getGoomba(16, 74));

        level.addBlock(Block.getUnbreakableBlock(), 4, 65);
        level.addBlock(Block.getUnbreakableBlock(), 5, 65);
        level.addBlock(Block.getUnbreakableBlock(), 6, 65);

        level.addBlock(Block.getUnbreakableBlock(), 14, 65);
        level.addBlock(Block.getUnbreakableBlock(), 15, 65);
        level.addBlock(Block.getUnbreakableBlock(), 16, 65);

        level.addBlock(Block.getUnbreakableBlock(), 10, 60);
        level.addBlock(Block.getUnbreakableBlock(), 11, 60);
        level.addBlock(Block.getUnbreakableBlock(), 12, 60);

        level.addBlock(Block.getUnbreakableBlock(), 4, 58);
        level.addBlock(Block.getUnbreakableBlock(), 5, 58);
        level.addBlock(Block.getUnbreakableBlock(), 6, 58);
        level.addBlock(Block.getUnbreakableBlock(), 7, 58);

        level.addBlock(Block.getUnbreakableBlock(), 15, 50);
        level.addBlock(Block.getUnbreakableBlock(), 16, 50);
        level.addBlock(Block.getUnbreakableBlock(), 17, 50);

        level.addBlock(Block.getUnbreakableBlock(), 10, 49);
        level.addBlock(Block.getUnbreakableBlock(), 11, 49);

        level.addBlock(Block.getUnbreakableBlock(), 5, 49);
        level.addBlock(Block.getUnbreakableBlock(), 6, 49);

        level.addBlock(LuckyBlock.getLuckyBlock(Mushroom.getMushroom()), 13, 40);

        level.addBlock(Block.getPipe()[0], 33, 48);
        level.addBlock(Block.getPipe()[1], 34, 48);
        level.addBlock(Block.getPipe()[2], 33, 49);
        level.addBlock(Block.getPipe()[3], 34, 49);
        level.addObject(Piranha.getPiranha(33.5f, 48));

        level.addBlock(Block.getPipe()[0], 39, 53);
        level.addBlock(Block.getPipe()[1], 40, 53);
        level.addBlock(Block.getPipe()[2], 39, 54);
        level.addBlock(Block.getPipe()[3], 40, 54);
        level.addObject(Piranha.getPiranha(39.5f, 53));

        level.addBlock(Block.getPipe()[0], 30, 58);
        level.addBlock(Block.getPipe()[1], 31, 58);
        level.addBlock(Block.getPipe()[2], 30, 59);
        level.addBlock(Block.getPipe()[3], 31, 59);
        level.addObject(Piranha.getPiranha(30.5f, 58));

        level.addBlock(Block.getPipe()[0], 32, 58);
        level.addBlock(Block.getPipe()[1], 33, 58);
        level.addBlock(Block.getPipe()[2], 32, 59);
        level.addBlock(Block.getPipe()[3], 33, 59);
        level.addObject(Piranha.getPiranha(32.5f, 58));

        level.addBlock(Block.getPipe()[0], 34, 58);
        level.addBlock(Block.getPipe()[1], 35, 58);
        level.addBlock(Block.getPipe()[2], 34, 59);
        level.addBlock(Block.getPipe()[3], 35, 59);
        level.addObject(Piranha.getPiranha(34.5f, 58));

        level.addBlock(Block.getPipe()[0], 39, 63);
        level.addBlock(Block.getPipe()[1], 40, 63);
        level.addBlock(Block.getPipe()[2], 39, 64);
        level.addBlock(Block.getPipe()[3], 40, 64);
        level.addObject(Piranha.getPiranha(39.5f, 63));

        level.addBlock(Block.getPipe()[0], 35, 63);
        level.addBlock(Block.getPipe()[1], 36, 63);
        level.addBlock(Block.getPipe()[2], 35, 64);
        level.addBlock(Block.getPipe()[3], 36, 64);
        level.addObject(Piranha.getPiranha(35.5f, 63));

        level.addBlock(Block.getPipe()[0], 30, 68);
        level.addBlock(Block.getPipe()[1], 31, 68);
        level.addBlock(Block.getPipe()[2], 30, 69);
        level.addBlock(Block.getPipe()[3], 31, 69);
        level.addObject(Piranha.getPiranha(30.5f, 68));

        level.addObject(Goomba.getGoomba(32, 69));

        level.addBlock(Block.getPipe()[0], 35, 68);
        level.addBlock(Block.getPipe()[1], 36, 68);
        level.addBlock(Block.getPipe()[2], 35, 69);
        level.addBlock(Block.getPipe()[3], 36, 69);
        level.addObject(Piranha.getPiranha(35.5f, 68));

        return level;
    }
}
