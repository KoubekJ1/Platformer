package renderer.actions;

import game.ProgramManager;
import game.level.Block;
import game.level.Level;
import game.level.character.enemy.Enemy;
import util.AssetManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 10000, 100);
        for (int i = 0; i < 10000; i++) {
            if (i == 10 || i == 11) continue;
            for (int j = 20; j < 100; j++) {
                testLevel.addBlock(new Block("blocks/brick.png", true), i, j);
            }
        }
        testLevel.addBlock(new Block("blocks/brick.png", true), 20, 16);
        testLevel.addBlock(new Block("blocks/brick.png", true), 25, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 35, 19);

        //testLevel.addEnemy(Enemy.getGoomba(30, 19));
        try {
            Enemy goomba = AssetManager.getEnemy("assets/enemies/goomba.enemy");
            goomba.setPosX(30);
            goomba.setPosY(19);
            testLevel.addEnemy(goomba);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        ProgramManager.play(testLevel);
    }
}
