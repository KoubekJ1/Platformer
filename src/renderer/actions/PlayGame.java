package renderer.actions;

import game.ProgramManager;
import game.level.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProgramManager.play(new Level("1", "1"));
    }
}
