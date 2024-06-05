package renderer.window;

import javax.swing.*;
import java.awt.*;

/**
 * Manages the window used for displaying the whole program
 */
public abstract class WindowManager {
    private static GameJFrame window;

    /**
     * Shows the window and initializes it if it hasn't already been initialized
     */
    public static void showWindow() {
        if (window == null || !window.isWindowInitialized()) {
            initializeWindow();
        }
        if (!window.isActive()) {
            window.setVisible(true);
        }
    }

    /**
     * Creates a menu screen with the given buttons
     * @param buttons the buttons in the menu screen
     * @param card the card id used for the menu screen
     */
    public static void createMenuCard(JButton[] buttons, String card) {
        window.createMenuCard(buttons, card);
    }

    /**
     * Initializes the window
     */
    private static void initializeWindow() {
        window = new GameJFrame();
        window.initialize();
    }

    /**
     * Displays the gameplay card used for rendering the game world and creates it if it hasn't already been created
     */
    public static void showGameplayCard() {
        if (!window.isGameplayCardInitialized()) {
            window.createGameplayCard();
        }
        switchCard("gameplay");
        window.requestFocus();
    }

    /**
     * Switches the displayed card to the given card
     * @param card the card to be shown
     */
    public static void switchCard(String card) {
        window.switchCards(card);
    }

    /**
     * Returns the resolution of the window
     * @return the resolution
     */
    public static int[] getResolution() {
        return new int[]{window.getContentPane().getWidth(), window.getContentPane().getHeight()};
    }

    /**
     * Returns the refresh rate of the screen
     * @return refresh rate
     */
    public static int getRefreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    }

    /**
     * Returns whether the given card has already been created
     * @param card the card
     * @return if it was created
     */
    public static boolean isCardCreated(String card) {
        return window.isCardCreated(card);
    }

    public static void createCard(JPanel panel, String card) {
        window.add(panel, card);
    }
}
