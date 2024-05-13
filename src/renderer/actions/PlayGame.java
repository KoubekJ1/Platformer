package renderer.actions;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.blocks.collision.Break;
import game.level.Level;
import game.level.blocks.collision.LuckyBlock;
import game.level.dynamicobject.enemy.Goomba;
import game.level.dynamicobject.enemy.Koopa;
import game.level.dynamicobject.enemy.Piranha;
import game.level.dynamicobject.player.powerups.states.Fire;
import game.level.dynamicobject.player.powerups.states.Mushroom;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 200, 30, Level.SKY_COLOR, 20, 19);
        for (int i = 0; i < 200; i++) {
            for (int j = 20; j < 30; j++) {
                testLevel.addBlock(Block.getGround(), i, j);
            }
        }

        for (int i = 0; i < 20; i++) {
            testLevel.addBlock(Block.getPipe()[2], 15, i);
            testLevel.addBlock(Block.getPipe()[3], 16, i);
        }

        testLevel.addObject(Goomba.getGoomba(30,19));
        testLevel.addBlock(Block.getPipe()[0], 35, 18);
        testLevel.addBlock(Block.getPipe()[1], 36, 18);
        testLevel.addBlock(Block.getPipe()[2], 35, 19);
        testLevel.addBlock(Block.getPipe()[3], 36, 19);

        testLevel.addObject(Koopa.getKoopa(50, 18));

        testLevel.addObject(Goomba.getGoomba(55, 19));
        testLevel.addObject(Goomba.getGoomba(57, 19));

        testLevel.addBlock(Block.getPipe()[0], 60, 18);
        testLevel.addBlock(Block.getPipe()[1], 61, 18);
        testLevel.addBlock(Block.getPipe()[2], 60, 19);
        testLevel.addBlock(Block.getPipe()[3], 61, 19);


        testLevel.addBlock(Block.getPipe()[0], 75, 18);
        testLevel.addBlock(Block.getPipe()[1], 76, 18);
        testLevel.addBlock(Block.getPipe()[2], 75, 19);
        testLevel.addBlock(Block.getPipe()[3], 76, 19);
        testLevel.addObject(Piranha.getPiranha(75.5f, 18));

        testLevel.addObject(Koopa.getKoopa(62, 18));
        testLevel.addObject(Goomba.getGoomba(72, 18));
        testLevel.addObject(Goomba.getGoomba(74, 18));

        for (int i = 65; i <= 71; i++) {
            testLevel.addBlock(Block.getBrick(), i, 15);
        }
        testLevel.addBlock(Block.getUnbreakableBlock(), 65, 15);
        testLevel.addBlock(Block.getUnbreakableBlock(), 71, 15);
        testLevel.addBlock(LuckyBlock.getLuckyBlock(Mushroom.getMushroom()), 68, 15);
        testLevel.addBlock(Block.getBrick(), 68, 11);

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= i; j++) {
                testLevel.addBlock(Block.getUnbreakableBlock(), 85 + i, 19 - j);
            }
        }
        testLevel.addBlock(Block.getUnbreakableBlock(), 97, 10);
        testLevel.addBlock(Block.getUnbreakableBlock(), 98, 10);
        testLevel.addBlock(Block.getUnbreakableBlock(), 99, 10);

        testLevel.addBlock(Block.getUnbreakableBlock(), 105, 10);
        testLevel.addBlock(Block.getUnbreakableBlock(), 106, 10);
        testLevel.addBlock(Block.getUnbreakableBlock(), 107, 10);

        testLevel.addBlock(LuckyBlock.getLuckyBlock(Fire.getFireflower()), 106, 6);

        testLevel.addBlock(Block.getPipe()[0], 112, 18);
        testLevel.addBlock(Block.getPipe()[1], 113, 18);
        testLevel.addBlock(Block.getPipe()[2], 112, 19);
        testLevel.addBlock(Block.getPipe()[3], 113, 19);

        testLevel.addBlock(Block.getPipe()[0], 129, 18);
        testLevel.addBlock(Block.getPipe()[1], 130, 18);
        testLevel.addBlock(Block.getPipe()[2], 129, 19);
        testLevel.addBlock(Block.getPipe()[3], 130, 19);

        testLevel.addObject(Goomba.getGoomba(115, 19));
        testLevel.addObject(Goomba.getGoomba(117, 19));
        testLevel.addObject(Goomba.getGoomba(119, 19));
        testLevel.addObject(Koopa.getKoopa(121, 18));
        testLevel.addObject(Goomba.getGoomba(123, 19));
        testLevel.addObject(Goomba.getGoomba(125, 19));
        testLevel.addObject(Goomba.getGoomba(127, 19));

        ProgramManager.play(testLevel);
    }
}
