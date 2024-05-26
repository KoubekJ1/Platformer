package renderer.window;

import game.ProgramManager;
import util.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GameJFrame extends JFrame implements KeyListener {

    private boolean windowInitialized = false;
    private boolean gameplayCardInitialized = false;
    private CardLayout cardLayout;

    private GameplayJPanel gameplayPanel;

    private HashMap<String, Boolean> isCardCreated;
    
    public void initialize() {
        isCardCreated = new HashMap<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Platformer");

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    public void createMenuCard(JButton[] buttons, String card) {
        JPanel panel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);

        gbc.gridx = 0;
        gbc.gridy = 0;
        for (JButton button : buttons) {
            if (button == null) continue;
            panel.add(button, gbc);
            gbc.gridy++;
        }

        this.add(panel, card);
        isCardCreated.put(card, true);
    }

    public void createGameplayCard() {
        if (!isCardCreated.getOrDefault("gameplay", false));
        gameplayPanel = new GameplayJPanel(true);
        this.add(gameplayPanel, "gameplay");

        gameplayPanel.initialize();
        gameplayCardInitialized = true;
        isCardCreated.put("gameplay", true);
    }

    public void switchCards(String card) {
        cardLayout.show(this.getContentPane(), card);

        this.revalidate();
        this.repaint();
    }

    public boolean isWindowInitialized() {
        return windowInitialized;
    }

    public boolean isGameplayCardInitialized() {
        return gameplayCardInitialized;
    }

    public void toggleFullscreen() {
        if (this.isUndecorated()) {
            this.dispose();
            this.setUndecorated(false);
            this.setVisible(true);
        } else {
            this.dispose();
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setUndecorated(true);
            this.setVisible(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        InputManager.pressKey(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            toggleFullscreen();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
            ProgramManager.pause();
        }
    }

    public boolean isCardCreated(String card) {
        return isCardCreated.containsKey(card);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputManager.releaseKey(e.getKeyCode());
    }
}