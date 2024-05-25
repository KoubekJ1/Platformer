package game;

import game.level.Level;
import renderer.actions.*;
import renderer.window.WindowManager;

import javax.swing.*;

public class ProgramManager {

    private static boolean debug;
    private static Level level;

    public static void startProgram(String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("debug")) {
                debug = true;
                System.out.println("Debug mode enabled!");
            }
        }

        // Initializes and shows the window
        WindowManager.showWindow();

        // Creates the menu screen
        JButton[] menuButtons = new JButton[2];
        menuButtons[0] = new JButton(new PlayGame());
        menuButtons[1] = new JButton(new Quit());
        //if (ProgramManager.isDebug()) menuButtons[2] = new JButton(new OpenAssetEditor());
        WindowManager.createMenuCard(menuButtons, "main-menu");
        WindowManager.switchCard("main-menu");
    }

    public static void play(Level level) {
        WindowManager.showGameplayCard();
        ProgramManager.level = level;
        ProgramManager.level.start();
    }

    public static void pause() {
        if (ProgramManager.level == null) return;
        if (!WindowManager.isCardCreated("pause")) {
            JButton[] buttons = new JButton[2];
            buttons[0] = new JButton(new Resume());
            buttons[1] = new JButton(new BackToMenu());
            WindowManager.createMenuCard(buttons, "pause");
        }
        level.stop();
        WindowManager.switchCard("pause");
    }

    public static void endLevel() {
        level.stop();
        level = null;
        WindowManager.switchCard("main-menu");
    }

    public static Level getLevel() {
        return level;
    }

    public static boolean isDebug() {
        return debug;
    }
}
