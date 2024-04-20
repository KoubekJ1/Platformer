package renderer.window;

import game.ProgramManager;
import game.level.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameplayJPanel extends JPanel {
    private RenderInfo renderInfo;
    private float baseBlockSize;

    public GameplayJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public void initialize() {
        Renderer.setPanel(this);
    }

    private void updateBaseBlockSize() {
        baseBlockSize = this.getWidth() / 60.0f;
    }

    public void render(RenderInfo renderInfo) {
        this.renderInfo = renderInfo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (renderInfo == null) {
            return;
        }
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
            if (player.getCurrentImage(baseBlockSize) == null) {
                throw new IllegalStateException("Player doesn't have an active image!");
            }
            g.drawImage(player.getCurrentImage(baseBlockSize), (int) (player.getPosition()[0] * baseBlockSize), (int) (player.getPosition()[1] * baseBlockSize), Color.black, null);
        }

        if (ProgramManager.isDebug()) {
            g.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + renderInfo.getFrameRate(), 100, 100);
        }
    }
}
