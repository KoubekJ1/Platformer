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

    @Override
    public void run() {
        try {
            while (true) {
                ProgramManager.getLevel().update(dt);
                endTime = Time.getTime();
                dt = endTime - beginTime;
                beginTime = endTime;
                sleep(16);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getClass() + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            WindowManager.switchCard("main-menu");
        }
    }
}
