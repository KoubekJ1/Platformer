package leveleditor.actions;

import game.level.blocks.Block;
import leveleditor.ObjectPanel;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectBlock extends AbstractAction {

    private Block block;
    private ObjectPanel parentPanel;

    public SelectBlock(Block block, ObjectPanel parentPanel) {
        this.block = block;
        this.parentPanel = parentPanel;
        putValue(Action.SMALL_ICON, new ImageIcon(block.getCurrentImage(WindowManager.getResolution()[1] / 50)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parentPanel.setSelectedBlock(block);
    }
}
