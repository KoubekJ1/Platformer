package util.asseteditor.actions;

import util.asseteditor.AssetEditor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpenAssetAction extends AbstractAction {

    public OpenAssetAction() {
        super("Open");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("assets");

        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;

                String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".enemy") || fileName.endsWith(".block") || fileName.endsWith(".powerup");
            }

            @Override
            public String getDescription() {
                return "Game asset file (*.enemy, *.powerup, *.block)";
            }
        });

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            AssetEditor.openAsset(fileChooser.getSelectedFile());
        }
    }
}
