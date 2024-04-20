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
        WindowManager.createMenuCard(new JButton[]{new JButton(new PlayGame()), new JButton(new Quit())}, "main-menu");
        WindowManager.switchCard("main-menu");
    }

    public static void play(Level level) {
        WindowManager.showGameplayCard();
        ProgramManager.level = level;
        ProgramManager.level.start();
    }

    public static Level getLevel() {
        return level;
    }

    public static boolean isDebug() {
        return debug;
    }
}
