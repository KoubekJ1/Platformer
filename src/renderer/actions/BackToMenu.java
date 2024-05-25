package renderer.actions;

import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BackToMenu extends AbstractAction {

    public BackToMenu() {
        super("Back to menu");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowManager.switchCard("main-menu");
    }
}
