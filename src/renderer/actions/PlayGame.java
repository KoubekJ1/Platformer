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
import util.Level1;
import util.Level2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Level1.getLevel().serialize();
            Level2.getLevel().serialize();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ProgramManager.play(Level2.getLevel());
    }
}
