package renderer.window;

import javax.swing.*;
import java.awt.*;

public abstract class WindowManager {
    private static GameJFrame window;

    public static void showWindow() {
        if (window == null || !window.isWindowInitialized()) {
            initializeWindow();
        }
        if (!window.isActive()) {
            window.setVisible(true);
        }
    }

    public static void createMenuCard(JButton[] buttons, String card) {
        window.createMenuCard(buttons, card);
    }


    private static void initializeWindow() {
        window = new GameJFrame();
        window.initialize();
    }


    public static void showGameplayCard() {
        if (!window.isGameplayCardInitialized()) {
            window.createGameplayCard();
        }
        switchCard("gameplay");
        window.requestFocus();
    }

    public static void switchCard(String card) {
        window.switchCards(card);
    }

    public static int[] getResolution() {
        return new int[]{window.getContentPane().getWidth(), window.getContentPane().getHeight()};
    }

    public static int getRefreshRate() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    }
}
