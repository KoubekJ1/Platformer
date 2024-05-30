package renderer.window;

import game.ProgramManager;
import util.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * The particular window used by the game
 */
public class GameJFrame extends JFrame implements KeyListener {

    private boolean windowInitialized = false;
    private boolean gameplayCardInitialized = false;
    private CardLayout cardLayout;

    private GameplayJPanel gameplayPanel;

    private HashMap<String, Boolean> isCardCreated;

    /**
     * Initializes the window including its size and layout
     */
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

    /**
     * Creates a menu screen with the given buttons
     * @param buttons the buttons in the menu screen
     * @param card the card id used for the menu screen
     */
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

    /**
     * Creates the gameplay card used for rendering the game world
     */
    public void createGameplayCard() {
        if (!isCardCreated.getOrDefault("gameplay", false));
        gameplayPanel = new GameplayJPanel(true);
        this.add(gameplayPanel, "gameplay");

        gameplayPanel.initialize();
        gameplayCardInitialized = true;
        isCardCreated.put("gameplay", true);
    }

    /**
     * Switches the displayed card to the given card
     * @param card the card to be shown
     */
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

    /**
     * Toggles fullscreen mode
     */
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

    /**
     * Returns whether the card has already been created
     * @param card the card
     * @return if it was created
     */
    public boolean isCardCreated(String card) {
        return isCardCreated.containsKey(card);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        InputManager.releaseKey(e.getKeyCode());
    }
}