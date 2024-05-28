package util;

import java.util.HashMap;

/**
 * Input manager is used to keep track of button presses and to ensure that they can be accessed from anywhere
 */
public abstract class InputManager {
    private static HashMap<Integer, Boolean> keyboardButtonPressed = new HashMap<>();

    /**
     * Marks the key as pressed down
     * @param button the key
     */
    public static void pressKey(int button) {
        keyboardButtonPressed.put(button, true);
    }

    /**
     * Marks the key as not pressed down
     * @param button the key
     */
    public static void releaseKey(int button) {
        keyboardButtonPressed.put(button, false);
    }

    /**
     * Gets if the key is pressed down
     * @param button the key
     * @return whether the button is pressed down
     */
    public static boolean isKeyPressed(int button) {
        return keyboardButtonPressed.getOrDefault(button, false);
    }

    /**
     * Sets all keys as released
     */
    public static void resetInputs() {
        keyboardButtonPressed.clear();
    }
}
