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
import renderer.window.GameJFrame;
import renderer.window.WindowManager;
import util.AssetManager;
import util.Level1;
import util.Level2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinkedList<Level> levels;
        try {
            levels = AssetManager.getLevels();
        } catch (Exception ex) {
            int answer = JOptionPane.showConfirmDialog(null, "Unable to load levels" + "\n" + ex.getClass() + "\n" + ex.getMessage() + "\n\nDo you wish to serialize the levels?\n(If you don't know what this means, click yes)", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (answer == 0) {
                SerializeLevels.serializeLevels();
            }
            return;
        }
        JButton[] buttons = new JButton[levels.size() + 1];
        int index = 0;
        for (Level level : levels) {
            buttons[index] = new JButton(new PlayLevel(level));
            index++;
        }
        buttons[buttons.length - 1] = new JButton(new BackToMenu());
        WindowManager.createMenuCard(buttons, "level-select");
        WindowManager.switchCard("level-select");
    }
}
