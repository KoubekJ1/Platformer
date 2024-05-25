package game.level;

import game.ProgramManager;
import renderer.window.WindowManager;
import util.Time;

import javax.swing.*;
import java.io.Serializable;

public class UpdateThread extends Thread implements Serializable {

    private float beginTime;
    private float endTime;
    private float dt;

    private boolean paused;

    @Override
    public void run() {
        paused = false;
        try {
            beginTime = Time.getTime();
            while (true) {
                if (!paused) {
                    ProgramManager.getLevel().update(dt);
                }
                endTime = Time.getTime();
                dt = endTime - beginTime;
                beginTime = endTime;
                try {
                    sleep(16);
                } catch (InterruptedException e) {

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass() + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            WindowManager.switchCard("main-menu");
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void unpause() {
        this.paused = false;
    }
}
