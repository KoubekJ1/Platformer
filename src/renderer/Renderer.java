package renderer;

import renderer.window.GameplayJPanel;

public abstract class Renderer {

    private static GameplayJPanel panel;

    public static void setPanel(GameplayJPanel panel) {
        if (panel == null) {
            throw new IllegalArgumentException("Panel is null!");
        }
        Renderer.panel = panel;
    }

    public static void render() {
        panel.render();
    }
}
