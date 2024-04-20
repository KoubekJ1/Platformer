package renderer.window;

import game.level.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameplayJPanel extends JPanel {
    private RenderInfo renderInfo;
    private float baseBlockSize;

    public void initialize() {
        Renderer.setGraphics((Graphics2D) this.getGraphics());
    }

    private void updateBaseBlockSize() {
        baseBlockSize = WindowManager.getResolution()[0] / 60.0f;
    }

    public void render(RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
        paintComponent(this.getGraphics());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Reset transform
        //g.setTransform(defaultTransform);

        // Render background
        g.setColor(renderInfo.getBackground());
        g.fillRect(0, 0, WindowManager.getResolution()[0], WindowManager.getResolution()[1]);

        // Setup
        updateBaseBlockSize();
        //g.setTransform(camera.getTransform());

        // Rendering the player(s)
        for (Player player : renderInfo.getPlayers()) {
            g.drawImage(player.getCurrentImage(baseBlockSize), (int) (player.getPosition()[0] * baseBlockSize), (int) (player.getPosition()[1] * baseBlockSize), null, null);
        }
    }
}
