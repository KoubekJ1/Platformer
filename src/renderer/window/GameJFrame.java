package renderer.window;

import util.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameJFrame extends JFrame implements KeyListener {

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

        this.addKeyListener(this);
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
        gameplayPanel = new GameplayJPanel(true);
        this.add(gameplayPanel, "gameplay");

        gameplayPanel.initialize();
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputManager.pressKey(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputManager.releaseKey(e.getKeyCode());
    }
}