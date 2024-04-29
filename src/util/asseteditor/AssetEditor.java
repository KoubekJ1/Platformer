package util.asseteditor;

import util.asseteditor.actions.NewAssetAction;
import util.asseteditor.actions.OpenAssetAction;
import util.asseteditor.actions.SaveAssetAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class AssetEditor {

    private static AssetEditor editor;
    private JFrame window;
    private AssetEditorPanel panel;

    public static void showAssetEditor() {
        editor = new AssetEditor();
        editor.initializeWindow();
        editor.showWindow();
    }

    public static void newAsset(int assetType) {
        AssetType type;
        switch (assetType) {
            case 0 -> type = AssetType.BLOCK;
            case 1 -> type = AssetType.ENEMY;
            case 2 -> type = AssetType.POWERUP;
            case 3 -> type = AssetType.SPRITE;
            case 4 -> type = AssetType.ANIMATION;
            default -> throw new IllegalArgumentException("Invalid asset type!");
        }
        editor.panel.setAsset(type);
    }

    public static void saveAsset() {

    }

    public static void openAsset(File file) {

    }

    private AssetEditor() {

    }

    private void initializeWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setMinimumSize(new Dimension(800, 600));
        window.setLocationRelativeTo(null);
        window.setTitle("Asset Editor");

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newAsset = new JMenuItem(new NewAssetAction());
        JMenuItem save = new JMenuItem(new SaveAssetAction());
        JMenuItem open = new JMenuItem(new OpenAssetAction());

        fileMenu.setMnemonic(KeyEvent.VK_F);
        newAsset.setMnemonic(KeyEvent.VK_N);
        save.setMnemonic(KeyEvent.VK_S);
        open.setMnemonic(KeyEvent.VK_O);

        fileMenu.add(newAsset);
        fileMenu.add(save);
        fileMenu.add(open);

        menu.add(fileMenu);

        window.setJMenuBar(menu);
        addPanel();
    }

    private void showWindow() {
        window.setVisible(true);
    }

    private void addPanel() {
        if (panel == null) panel = new AssetEditorPanel();
        window.add(panel);
    }
}
