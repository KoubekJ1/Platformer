package renderer.actions;

import game.ProgramManager;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The button that returns the player back to the main menu
 */
public class BackToMenu extends AbstractAction {

    public BackToMenu() {
        super("Back to menu");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ProgramManager.getLevel() != null) {
            ProgramManager.endLevel();
        } else {
            WindowManager.switchCard("main-menu");
        }
    }
}
