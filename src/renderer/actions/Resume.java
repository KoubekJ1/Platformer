package renderer.actions;

import game.ProgramManager;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Resume extends AbstractAction {

    public Resume() {
        super("Resume");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowManager.switchCard("gameplay");
        ProgramManager.getLevel().resume();
    }
}
