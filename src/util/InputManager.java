package util;

import java.util.HashMap;

public abstract class InputManager {
    private static HashMap<Integer, Boolean> keyboardButtonPressed = new HashMap<>();

    public static void pressKey(int button) {
        keyboardButtonPressed.put(button, true);
    }

    public static void releaseKey(int button) {
        keyboardButtonPressed.put(button, false);
    }

    public static boolean isKeyPressed(int button) {
        return keyboardButtonPressed.getOrDefault(button, false);
    }
}
