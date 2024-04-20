package util;

import java.util.HashMap;

public abstract class InputManager {
    private static HashMap<Integer, Boolean> keyboardButtonPressed = new HashMap<>();

    public static void pressButton(int button) {
        keyboardButtonPressed.put(button, true);
    }

    public static void releaseButton(int button) {
        keyboardButtonPressed.put(button, false);
    }

    public static boolean getButtonPressed(int button) {
        return keyboardButtonPressed.getOrDefault(button, false);
    }
}
