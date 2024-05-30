package renderer.actions;

import game.ProgramManager;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The button used for resuming the game if it has been paused
 */
public class Resume extends AbstractAction {

    public Resume() {
        super("Resume");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowManager.switchCard("gameplay");
        ProgramManager.resume();
    }
}
