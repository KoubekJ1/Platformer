package leveleditor.actions;

import leveleditor.ObjectPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SwitchObjectType extends AbstractAction {

    private ObjectPanel parentPanel;
    private String card;

    public SwitchObjectType(String displayedText, Icon icon, String card, ObjectPanel parentPanel) {
        super(displayedText, icon);
        this.card = card;
        this.parentPanel = parentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentPanel.switchCard(card);
    }
}
