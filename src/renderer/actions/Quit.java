package renderer.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Quit extends AbstractAction {

    public Quit() {
        super("Quit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
