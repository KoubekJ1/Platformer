package leveleditor.actions;

import leveleditor.ObjectPanel;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SwitchObjectType extends AbstractAction {

    private ObjectPanel parentPanel;
    private String card;

    public SwitchObjectType(Icon icon, String card, ObjectPanel parentPanel) {
        putValue(Action.SMALL_ICON, icon);
        this.card = card;
        this.parentPanel = parentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentPanel.switchCard(card);
    }
}
