package leveleditor;

import renderer.window.GameplayJPanel;

import javax.swing.*;
import java.awt.*;

public class LevelEditorPanel extends JPanel {

    private static LevelEditorPanel levelEditorPanel;

    private ObjectPanel objectPanel;
    private GameplayJPanel levelPanel;
    private JPanel propertiesPanel;
    private JToolBar toolBar;

    private LevelEditorPanel() {
        this.setLayout(new BorderLayout(5, 5));

        this.objectPanel = new ObjectPanel();
        this.levelPanel = new GameplayJPanel();
        this.propertiesPanel = new JPanel();
        this.toolBar = new JToolBar("Tools");

        this.add(levelPanel, BorderLayout.CENTER);
        this.add(objectPanel, BorderLayout.WEST);
        this.add(propertiesPanel, BorderLayout.EAST);
        this.add(toolBar, BorderLayout.NORTH);
    }

    public static LevelEditorPanel get() {
        return levelEditorPanel;
    }
}
