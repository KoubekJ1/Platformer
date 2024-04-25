package renderer.actions;

import game.ProgramManager;
import game.level.Block;
import game.level.Level;
import game.level.character.enemy.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 10000, 50);
        for (int i = 0; i < 10000; i++) {
            if (i == 10 || i == 11) continue;
            for (int j = 20; j < 30; j++) {
                testLevel.addBlock(new Block("blocks/brick.png", true), i, j);
            }
        }
        testLevel.addBlock(new Block("blocks/brick.png", true), 20, 16);
        testLevel.addBlock(new Block("blocks/brick.png", true), 25, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 35, 19);

        testLevel.addEnemy(Enemy.getGoomba(30, 19));

        ProgramManager.play(testLevel);
    }
}
