package game;

import renderer.actions.*;
import renderer.window.WindowManager;

import javax.swing.*;

public class ProgramManager {
    public static void startProgram(String[] args) {
        // Initializes and shows the window
        WindowManager.showWindow();

        // Creates the menu screen
        WindowManager.createMenuCard(new JButton[]{new JButton(new PlayGame()), new JButton(new Quit())}, "mainMenu");
    }

    public static void play() {
        WindowManager.showGameplayCard();
    }
}
