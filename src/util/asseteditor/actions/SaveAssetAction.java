package util.asseteditor.actions;

import util.asseteditor.AssetEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveAssetAction extends AbstractAction {

    public SaveAssetAction() {
        super("Save");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AssetEditor.saveAsset();
    }
}
