package renderer.window;

import javax.swing.*;
import java.awt.*;

public class GameJFrame extends JFrame {

    private boolean windowInitialized = false;
    private boolean gameplayCardInitialized = false;
    private CardLayout cardLayout;

    private GameplayJPanel gameplayPanel;
    
    public void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Platformer");

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.setMinimumSize(new Dimension(800, 600));

        this.setLocationRelativeTo(null);
    }

    public void createMenuCard(JButton[] buttons, String card) {
        JPanel panel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        for (JButton button : buttons) {
            panel.add(button, gbc);
            gbc.gridy++;
        }

        this.add(panel, card);
    }

    public void createGameplayCard() {
        gameplayPanel = new GameplayJPanel();
        gameplayPanel.initialize();

        this.add(gameplayPanel, "gameplay");
        gameplayCardInitialized = true;
    }

    public void switchCards(String card) {
        cardLayout.show(this.getContentPane(), card);
    }

    public boolean isWindowInitialized() {
        return windowInitialized;
    }

    public boolean isGameplayCardInitialized() {
        return gameplayCardInitialized;
    }
}