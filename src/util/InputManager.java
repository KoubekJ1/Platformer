package util;

import java.util.HashMap;

public abstract class InputManager {
    private static HashMap<Integer, Boolean> keyboardButtonPressed = new HashMap<>();

    public void pressButton(int button) {
        keyboardButtonPressed.put(button, true);
    }

    public void releaseButton(int button) {
        keyboardButtonPressed.put(button, false);
    }

    public boolean getButtonPressed(int button) {
        return keyboardButtonPressed.getOrDefault(button, false);
    }
}
