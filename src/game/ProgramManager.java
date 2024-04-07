package game;

import game.actions.*;
import renderer.window.WindowManager;

import javax.swing.*;

public class ProgramManager {

    private static boolean debug;

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
        WindowManager.createMenuCard(new JButton[]{new JButton(new PlayGame()), new JButton(new Quit())}, "mainMenu");
    }

    public static void play() {
        WindowManager.showGameplayCard();
    }

    public static boolean isDebug() {
        return debug;
    }

    private static void setDebug(boolean debug) {
        ProgramManager.debug = debug;
    }
}
