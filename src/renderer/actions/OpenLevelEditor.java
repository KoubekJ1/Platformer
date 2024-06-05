package renderer.actions;

import game.ProgramManager;
import leveleditor.LevelEditorPanel;
import renderer.window.WindowManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpenLevelEditor extends AbstractAction
{

    public OpenLevelEditor() {
        super("Open Level Editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!WindowManager.isCardCreated("level-editor")) {
            WindowManager.createCard(LevelEditorPanel.get(), "level-editor");
        }
        WindowManager.switchCard("level-editor");
    }
}
