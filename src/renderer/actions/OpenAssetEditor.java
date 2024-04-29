package renderer.actions;

import util.AssetEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpenAssetEditor extends AbstractAction {

    public OpenAssetEditor() {
        super("Asset Editor");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AssetEditor.showAssetEditor();
    }
}
