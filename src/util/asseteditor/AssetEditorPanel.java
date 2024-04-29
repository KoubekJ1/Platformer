package util.asseteditor;

import util.AssetManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class AssetEditorPanel extends JPanel implements ActionListener {

    private Asset asset;

    private JPanel idPanel;
    private JLabel texture;
    private JPanel components;
    private JPanel sprite;

    private JTextField id;
    private JTextField name;

    public AssetEditorPanel() {
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(2, 2, 10, 10));

        createComponents();
    }

    public void setAsset(AssetType type) {
        asset = new Asset();
        asset.setAsset(Asset.getNewAsset(type));
        enableComponents();
    }

    public void createComponents() {
        id = new JTextField();
        id.setEnabled(false);
        name = new JTextField();
        name.setEnabled(false);

        idPanel = new JPanel();
        idPanel.setLayout(new GridLayout(4, 1, 0, 0));
        idPanel.add(new JLabel("Asset name:"));
        idPanel.add(name);
        idPanel.add(new JLabel("Asset ID:"));
        idPanel.add(id);

        components = new JPanel();

        texture = new JLabel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                ImageIcon icon;
                if (texture.getIcon() != null) icon = (ImageIcon) texture.getIcon();
                else icon = new ImageIcon(AssetManager.getTexture("default.png"));
                setShownTexture(icon, (int) (this.getWidth() / 2), (int) (this.getWidth() / 2));
            }
        };
        texture.setVerticalAlignment(JLabel.CENTER);
        texture.setHorizontalAlignment(JLabel.CENTER);

        sprite = new JPanel();

        this.add(idPanel);
        this.add(texture);
        this.add(components);
        this.add(sprite);
    }

    public void setShownTexture(ImageIcon imageIcon, int width, int height) {
        BufferedImage scaledBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(imageIcon.getImage(), 0, 0, null);
        Image scaledImage = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        texture.setIcon(new ImageIcon(scaledBufferedImage));
    }

    public void enableComponents() {
        id.setEnabled(true);
        name.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
