package game.level;

import game.ProgramManager;
import util.Time;

public class UpdateThread extends Thread {

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
                sleep(17);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.interrupt();
        }
    }
}
