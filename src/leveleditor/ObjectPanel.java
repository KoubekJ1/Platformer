package leveleditor;

import game.level.blocks.Block;
import game.level.dynamicobject.DynamicObject;
import leveleditor.actions.SelectBlock;
import leveleditor.actions.SwitchObjectType;
import util.AssetManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ObjectPanel extends JPanel {

    private Block selectedBlock;
    private DynamicObject selectedObject;

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

        blocks.setLayout(new FlowLayout());
        dynamicObjects.setLayout(new FlowLayout());

        this.add(objects, BorderLayout.CENTER);

        objectTypeSelector = new JToolBar("Object type");
        objectTypeSelector.setFloatable(false);
        objectTypeSelector.add(new JButton(new SwitchObjectType(new ImageIcon(AssetManager.getTexture("blocks/brick.png")), "blocks", this)));
        objectTypeSelector.add(new JButton(new SwitchObjectType(new ImageIcon(AssetManager.getTexture("characters/player/small/air.png")), "dynamicObjects", this)));

        this.add(objectTypeSelector, BorderLayout.NORTH);

        switchCard("blocks");
    }

    public void switchCard(String card) {
        objectSelectorCardLayout.show(objects, card);
    }

    private void loadObjects() {
        loadBlocks();
        loadDynamicObjects();
    }

    private void loadBlocks() {
        File[] files = new File("assets/blocks").listFiles();

        for (File file : files) {
            try {
                Block block = AssetManager.getBlock(file.getPath());
                JButton button = new JButton(new SelectBlock(block, this));
                blocks.add(button);
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }

    private void loadDynamicObjects() {
        File[] files = new File("assets/blocks").listFiles();

        for (File file : files) {
            try {
                Block block = AssetManager.getBlock(file.getPath());
                JButton button = new JButton(new SelectBlock(block, this));
                blocks.add(button);
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public void setSelectedBlock(Block selectedBlock) {
        this.selectedBlock = selectedBlock;
        this.selectedBlock = null;
    }

    public DynamicObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(DynamicObject selectedObject) {
        this.selectedObject = selectedObject;
        this.selectedBlock = null;
    }
}
