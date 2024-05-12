package renderer.actions;

import game.ProgramManager;
import game.level.blocks.Block;
import game.level.blocks.collision.Collidable;
import game.level.Level;
import game.level.dynamicobject.enemy.Goomba;
import game.level.dynamicobject.enemy.Koopa;
import game.level.dynamicobject.enemy.Piranha;
import game.level.dynamicobject.player.powerups.states.Fire;
import game.level.dynamicobject.player.powerups.states.Mushroom;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PlayGame extends AbstractAction {

    public PlayGame() {
        super("Play game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Level testLevel = new Level("1", "1", 10000, 100, Level.SKY_COLOR);
        for (int i = 0; i < 10000; i++) {
            if (i == 10 || i == 11) continue;
            for (int j = 20; j < 100; j++) {
                testLevel.addBlock(new Block("blocks/brick.png", true), i, j);
            }
        }
        testLevel.addBlock(new Block("blocks/brick.png", true, new Collidable() {
            @Override
            public void hit(int blockX, int blockY) {
                System.out.println("Block collision: " + blockX + ", " + blockY);
            }
        }), 20, 16);
        testLevel.addBlock(new Block("blocks/brick.png", true), 25, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 35, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 45, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 55, 19);
        testLevel.addBlock(new Block("blocks/brick.png", true), 65, 19);

        testLevel.addBlock(new Block("blocks/pipe/ul.png", true), 5, 18);
        testLevel.addBlock(new Block("blocks/pipe/ur.png", true), 6, 18);
        testLevel.addBlock(new Block("blocks/pipe/ll.png", true), 5, 19);
        testLevel.addBlock(new Block("blocks/pipe/lr.png", true), 6, 19);

        testLevel.addObject(Piranha.getPiranha(5.5f, 18));

        testLevel.addObject(Goomba.getGoomba(30, 19));
        testLevel.addObject(Goomba.getGoomba(44, 19));
        testLevel.addObject(Koopa.getKoopa(40, 18));
        testLevel.addObject(Mushroom.getMushroom(52, 19));
        testLevel.addObject(Fire.getFireflower(62, 19));
        /*try {
            Enemy goomba = AssetManager.getEnemy("assets/enemies/goomba.enemy");
            goomba.setPosX(30);
            goomba.setPosY(19);
            testLevel.addObject(goomba);
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }*/

        ProgramManager.play(testLevel);
    }
}
