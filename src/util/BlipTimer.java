package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BlipTimer implements ActionListener {

    private static BlipTimer blipTimer = new BlipTimer();

    public static boolean isBlip() {
        if (!blipTimer.timer.isRunning()) blipTimer.timer.start();
        return blipTimer.blip;
    }

    private boolean blip = true;
    private Timer timer = new Timer(100, this);

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) blip = !blip;
    }
}
