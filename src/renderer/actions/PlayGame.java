package renderer.actions;

import game.ProgramManager;
import game.level.Block;
import game.level.Level;
import game.level.enemy.Enemy;
import renderer.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 1000, 250);
        for (int i = 0; i < 1000; i++) {
            if (i == 10 || i == 11) continue;
            testLevel.addBlock(new Block("blocks/brick.png", true), i, 20);
        }
        testLevel.addBlock(new Block("blocks/brick.png", true), 20, 17);
        testLevel.addBlock(new Block("blocks/brick.png", true), 25, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 35, 19);

        testLevel.addEnemy(Enemy.getGoomba(30, 19));

        ProgramManager.play(testLevel);
    }
}
