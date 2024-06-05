package leveleditor;

import leveleditor.actions.SwitchObjectType;
import util.AssetManager;

import javax.swing.*;
import java.awt.*;

public class ObjectPanel extends JPanel {

    private JPanel objects;
    private CardLayout objectSelectorCardLayout;

    private JPanel blocks;
    private JPanel dynamicObjects;

    private JToolBar objectTypeSelector;

    public ObjectPanel() {
        this.setLayout(new BorderLayout());

        objects = new JPanel();
        objects.setLayout((objectSelectorCardLayout = new CardLayout()));

        objects.add((blocks = new JPanel()), "blocks");
        objects.add((dynamicObjects = new JPanel()), "dynamicObjects");

        this.add(objects, BorderLayout.CENTER);

        objectTypeSelector = new JToolBar("Object type");
        objectTypeSelector.setFloatable(false);
        objectTypeSelector.add(new JButton(new SwitchObjectType("Blocks", new ImageIcon(AssetManager.getTexture("blocks/brick.png")), "blocks", this)));
        objectTypeSelector.add(new JButton(new SwitchObjectType("Dynamic Objects", new ImageIcon(AssetManager.getTexture("characters/player/small/air.png")), "dynamicObjects", this)));

        this.add(objectTypeSelector, BorderLayout.NORTH);

        switchCard("blocks");
    }

    public void switchCard(String card) {
        objectSelectorCardLayout.show(objects, card);
    }
}
