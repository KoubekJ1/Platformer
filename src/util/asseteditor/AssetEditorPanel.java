package util.asseteditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AssetEditorPanel extends JPanel {

    private Asset asset;

    public AssetEditorPanel() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(2, 2, 10, 10));


    }

    public void setAsset(AssetType type) {
        asset = new Asset();
        asset.setAsset(Asset.getNewAsset(type));
    }

}
