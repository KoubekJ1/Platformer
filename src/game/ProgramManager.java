package game;

import game.level.Level;
import renderer.actions.*;
import renderer.window.WindowManager;

import javax.swing.*;

/**
 * ProgramManager is used for the basic functionality of the program, including starting and playing levels
 */
public class ProgramManager {

    private static boolean debug;
    private static Level level;

    /**
     * Starts the game and shows the window with the main menu
     * @param args program arguments (used for enabling debug mode)
     */
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
        JButton[] menuButtons = new JButton[4];
        menuButtons[0] = new JButton(new PlayGame());
        menuButtons[1] = new JButton(new Quit());
        menuButtons[2] = new JButton(new OpenLevelEditor());
        if (ProgramManager.isDebug()) menuButtons[3] = new JButton(new SerializeLevels());
        WindowManager.createMenuCard(menuButtons, "main-menu");
        WindowManager.switchCard("main-menu");
    }

    /**
     * Plays the given level
     * @param level the level
     */
    public static void play(Level level) {
        WindowManager.showGameplayCard();
        ProgramManager.level = level;
        ProgramManager.level.start();
    }

    /**
     * Pauses the game
     */
    public static void pause() {
        if (ProgramManager.level == null) return;
        if (!WindowManager.isCardCreated("pause")) {
            JButton[] buttons = new JButton[2];
            buttons[0] = new JButton(new Resume());
            buttons[1] = new JButton(new BackToMenu());
            WindowManager.createMenuCard(buttons, "pause");
        }
        level.pause();
        WindowManager.switchCard("pause");
    }

    /**
     * Resumes the game
     */
    public static void resume() {
        level.resume();
    }

    /**
     * Ends the level and returns the player back to the main menu
     */
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
