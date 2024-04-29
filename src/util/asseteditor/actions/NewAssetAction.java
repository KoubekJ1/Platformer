package util.asseteditor.actions;

import util.asseteditor.AssetEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewAssetAction extends AbstractAction {

    public NewAssetAction() {
        super("New");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String assetTypes[] = new String[]{"Block", "Enemy", "Power-up", "Sprite", "Animation"};
        JComboBox comboBox = new JComboBox<>(assetTypes);
        JOptionPane.showConfirmDialog(null, comboBox, "New asset", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
        AssetEditor.newAsset(comboBox.getSelectedIndex());
    }
}
