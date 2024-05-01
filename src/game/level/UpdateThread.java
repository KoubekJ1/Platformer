package game.level;

import game.ProgramManager;

public class UpdateThread extends Thread {
    @Override
    public void run() {
        try {
            while (true) {
                ProgramManager.getLevel().update();
                sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.interrupt();
        }
    }
}
