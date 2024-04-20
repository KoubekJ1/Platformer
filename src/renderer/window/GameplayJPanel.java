package renderer.window;

import game.ProgramManager;
import game.level.Player;
import renderer.RenderInfo;
import renderer.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameplayJPanel extends JPanel {
    private RenderInfo renderInfo;
    private float baseBlockSize;

    private static AffineTransform defaultTransform;
    private static AffineTransform currentTransform;

    public GameplayJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public void initialize() {
        Renderer.setPanel(this);
        defaultTransform = ((Graphics2D) this.getGraphics()).getTransform();
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
        Graphics2D g2D = (Graphics2D) g;
        if (renderInfo == null) {
            return;
        }

        // Reset transform
        g2D.setTransform(defaultTransform);

        // Render background
        g2D.setColor(renderInfo.getBackground());
        //g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Setup
        updateBaseBlockSize();
        currentTransform = (AffineTransform) renderInfo.getCamera().getTransform().clone();
        currentTransform.concatenate(defaultTransform);
        g2D.setTransform(currentTransform);

        // Rendering the player(s)
        for (Player player : renderInfo.getPlayers()) {
            if (player.getCurrentImage(baseBlockSize) == null) {
                throw new IllegalStateException("Player doesn't have an active image!");
            }
            g2D.drawImage(player.getCurrentImage(baseBlockSize), (int) (player.getPosition()[0] * baseBlockSize), (int) (player.getPosition()[1] * baseBlockSize), Color.black, null);
        }

        if (ProgramManager.isDebug()) {
            g2D.setTransform(defaultTransform);
            g2D.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            g2D.setColor(Color.BLACK);
            g2D.drawString("FPS: " + renderInfo.getFrameRate(), 100, 100);
        }
    }
}
