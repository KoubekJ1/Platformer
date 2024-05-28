package renderer;

import renderer.window.GameplayJPanel;

/**
 * Renderer serves as the game's rendering manager
 */
public abstract class Renderer {

    private static GameplayJPanel panel;

    /**
     * Sets the GameplayJPanel to be used for rendering
     * @param panel the panel
     */
    public static void setPanel(GameplayJPanel panel) {
        if (panel == null) {
            throw new IllegalArgumentException("Panel is null!");
        }
        Renderer.panel = panel;
    }

    /**
     * Renders the level on the selected panel
     */
    public static void render() {
        panel.render();
    }
}
