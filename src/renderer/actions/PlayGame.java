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

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProgramManager.play(Level1.getLevel());
    }
}
