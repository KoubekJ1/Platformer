package leveleditor.actions;

import game.level.blocks.Block;
import game.level.dynamicobject.DynamicObject;
import leveleditor.ObjectPanel;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectDynamicObject extends AbstractAction {
    private DynamicObject dynamicObject;
    private ObjectPanel parentPanel;

    public SelectDynamicObject(DynamicObject dynamicObject, ObjectPanel parentPanel) {
        this.dynamicObject = dynamicObject;
        this.parentPanel = parentPanel;
        putValue(Action.SMALL_ICON, new ImageIcon(dynamicObject.getCurrentImage(WindowManager.getResolution()[1] / 50)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentPanel.setSelectedObject(dynamicObject);
    }
}
