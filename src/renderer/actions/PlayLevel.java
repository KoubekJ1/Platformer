package renderer.actions;

import game.ProgramManager;
import game.level.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayLevel extends AbstractAction {

    private Level level;

    public PlayLevel(Level level) {
        super(level.getLevelName());
        this.level = level;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProgramManager.play(level);
    }
}
