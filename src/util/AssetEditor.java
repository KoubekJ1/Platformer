package util;

import javax.swing.*;
import java.awt.*;

public class AssetEditor {

    private static AssetEditor editor;
    private JFrame window;

    public static void showAssetEditor() {
        editor = new AssetEditor();
        editor.initializeWindow();
        editor.showWindow();
    }

    private AssetEditor() {

    }

    private void initializeWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(new CardLayout());

        initializeBlockCard("block");
        initializeEnemyCard("enemy");
        initializePowerUpCard("power");
    }

    private void showWindow() {
        window.setVisible(true);
    }

    private void initializeMainPanel() {

    }

    private void initializeBlockCard(String name) {
        JPanel blockPanel = new JPanel();
    }

    private void initializeEnemyCard(String name) {

    }

    private void initializePowerUpCard(String name) {

    }



}
