package renderer.actions;

import game.ProgramManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play Snake!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProgramManager.play();
    }
}
