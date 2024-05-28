package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * BlipTimer contains a timer used as a unified blip timer for sprites to prevent each sprite in the level having its own timer
 */
public class BlipTimer implements ActionListener {

    private static BlipTimer blipTimer = new BlipTimer();

    /**
     * Gets whether the flashing sprite should be invisible
     * @return whether the sprite should be invisible
     */
    public static boolean isBlip() {
        if (!blipTimer.timer.isRunning()) blipTimer.timer.start();
        return blipTimer.blip;
    }

    private boolean blip = true;
    private Timer timer = new Timer(100, this);

    /**
     * Invoked by the blip timer and flips the blip value
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) blip = !blip;
    }
}
