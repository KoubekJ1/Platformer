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
        Level testLevel = new Level("1", "1", 200, 30, Level.SKY_COLOR, 10, 19);
        for (int i = 0; i < 200; i++) {
            for (int j = 20; j < 30; j++) {
                testLevel.addBlock(Block.getGround(), i, j);
            }
        }

        testLevel.addObject(Goomba.getGoomba(20, 19));

        ProgramManager.play(testLevel);
    }
}
