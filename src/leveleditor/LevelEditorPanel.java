package leveleditor;

import renderer.window.GameplayJPanel;

import javax.swing.*;
import java.awt.*;

public class LevelEditorPanel extends JPanel {
    private ObjectPanel objectPanel;
    private GameplayJPanel levelPanel;
    private JPanel propertiesPanel;
    private JToolBar toolBar;

    public LevelEditorPanel() {
        this.setLayout(new BorderLayout(5, 5));

        this.add(levelPanel, BorderLayout.CENTER);
        this.add(objectPanel, BorderLayout.WEST);
        this.add(propertiesPanel, BorderLayout.EAST);
        this.add(toolBar, BorderLayout.NORTH);
    }
}
