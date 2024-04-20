package renderer.actions;

import game.ProgramManager;
import game.level.Block;
import game.level.Level;
import renderer.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 250, 250);
        for (int i = 0; i < 250; i++) {
            if (i == 10 || i == 11) continue;
            testLevel.addBlock(new Block("blocks/brick.png", true), i, 20);
        }
        ProgramManager.play(testLevel);
    }
}
